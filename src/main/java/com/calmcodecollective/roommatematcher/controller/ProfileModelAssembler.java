package com.calmcodecollective.roommatematcher.controller;

import com.calmcodecollective.roommatematcher.dto.ProfileDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProfileModelAssembler implements RepresentationModelAssembler<ProfileDto, EntityModel<ProfileDto>> {

    @Override
    public EntityModel<ProfileDto> toModel(ProfileDto profile) {
        return EntityModel.of(profile,
                linkTo(methodOn(ProfileController.class).one(profile.getId())).withSelfRel(),
                linkTo(methodOn(ProfileController.class).all()).withRel("profiles"));
    }
}
