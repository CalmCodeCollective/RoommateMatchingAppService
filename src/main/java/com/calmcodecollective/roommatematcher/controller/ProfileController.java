package com.calmcodecollective.roommatematcher.controller;

import com.calmcodecollective.roommatematcher.dto.ProfileDto;
import com.calmcodecollective.roommatematcher.exception.AccountNotFoundException;
import com.calmcodecollective.roommatematcher.exception.ProfileNotFoundException;
import com.calmcodecollective.roommatematcher.model.roommate.Account;
import com.calmcodecollective.roommatematcher.model.roommate.AccountRepository;
import com.calmcodecollective.roommatematcher.model.roommate.Profile;
import com.calmcodecollective.roommatematcher.model.roommate.ProfileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProfileController {
    private final ProfileRepository repository;
    private final ProfileModelAssembler assembler;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public ProfileController(ProfileRepository repository, ProfileModelAssembler assembler,
                             AccountRepository accountRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.assembler = assembler;
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/profiles")
    CollectionModel<EntityModel<ProfileDto>> all() {
        List<EntityModel<ProfileDto>> profiles = repository.findAll().stream()
                .map(this::toDto)
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(profiles, linkTo(methodOn(ProfileController.class).all()).withSelfRel());
    }

    @PostMapping("/profiles")
    ResponseEntity<EntityModel<ProfileDto>> create(@RequestBody ProfileDto profileDto) {
        Profile profile = toEntity(profileDto);
        EntityModel<ProfileDto> entityModel = assembler.toModel(toDto(repository.save(profile)));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/profiles/{id}")
    EntityModel<ProfileDto> one(@PathVariable Long id) {
        Profile profile = repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
        return assembler.toModel(toDto(profile));
    }

    @PutMapping("/profiles/{id}")
    ResponseEntity<EntityModel<ProfileDto>> replace(@RequestBody ProfileDto profileDto, @PathVariable Long id) {
        Profile profile = repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));

        profile.setBio(profileDto.getBio());
        profile.setCleanlinessPref(profileDto.getCleanlinessPref());
        profile.setSleepSchedule(profileDto.getSleepSchedule());
        profile.setSmokingPref(profileDto.getSmokingPref());
        profile.setDrinkingPref(profileDto.getDrinkingPref());
        profile.setPetPref(profileDto.getPetPref());
        if (profileDto.getAccountId() != null) {
            profile.setAccount(findAccount(profileDto.getAccountId()));
        }

        return ResponseEntity.ok(assembler.toModel(toDto(repository.save(profile))));
    }

    @DeleteMapping("/profiles/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ProfileNotFoundException(id);
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Profile toEntity(ProfileDto profileDto) {
        Profile profile = modelMapper.map(profileDto, Profile.class);
        profile.setAccount(findAccount(profileDto.getAccountId()));
        return profile;
    }

    private ProfileDto toDto(Profile profile) {
        ProfileDto profileDto = modelMapper.map(profile, ProfileDto.class);
        if (profile.getAccount() != null) {
            profileDto.setAccountId(profile.getAccount().getId());
        }
        return profileDto;
    }

    private Account findAccount(Long accountId) {
        if (accountId == null) {
            throw new AccountNotFoundException(null);
        }
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}
