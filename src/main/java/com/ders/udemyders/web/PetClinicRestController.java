package com.ders.udemyders.web;

import com.ders.udemyders.exception.InternalServerException;
import com.ders.udemyders.exception.OwnerNotFoundException;
import com.ders.udemyders.model.Owner;
import com.ders.udemyders.service.PetClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Constraint;
import javax.validation.ConstraintViolationException;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class PetClinicRestController {
    private final PetClinicService petClinicService;

    @RequestMapping(method = RequestMethod.DELETE, value = "/owner/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable("id") Long id) {
        try {
            petClinicService.deleteOwner(id);
            return ResponseEntity.ok().build();
        } catch (OwnerNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/owner/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable("id") Long id, @RequestBody Owner ownerRequest) {
        try {
            Owner owner = petClinicService.findOwner(id);
            owner.setFirstName(ownerRequest.getFirstName());
            owner.setLastName(ownerRequest.getLastName());
            petClinicService.updateOwner(owner);
            return ResponseEntity.ok().build();
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/owner")
    public ResponseEntity<URI> createOwner(@RequestBody Owner owner) {
        try {
            petClinicService.createOwner(owner);
            Long id = owner.getId();
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(uri).build();
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Cacheable("allOwners")
    @RequestMapping(method = RequestMethod.GET, value = "/owners", produces = {"application/json", "application/xml"})
    public ResponseEntity<List<Owner>> getOwners() {
        System.out.println(">>>>>inside getOwners");
        List<Owner> owners = petClinicService.findOwners();
        return ResponseEntity.ok(owners);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/owner")
    public ResponseEntity<List<Owner>> getOwners(@RequestParam("ln") String lastname) {
        List<Owner> owners = petClinicService.findOwners(lastname);
        return ResponseEntity.ok(owners);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/owner/{id}", produces = "application/xml")
    public ResponseEntity<Owner> getOwner(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(petClinicService.findOwner(id));
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/owner/{id}", produces = "application/json")
    public ResponseEntity<?> getOwnerAsHateoasResource(@PathVariable("id") Long id) {
        try {
            Owner owner = petClinicService.findOwner(id);
            Link self = WebMvcLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner/" + id).withSelfRel();
            Link create = WebMvcLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner").withRel("create").withType("POST");
            Link update = WebMvcLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner/" + id).withRel("update");
            Link delete = WebMvcLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner/" + id).withRel("delete");
            EntityModel<Owner> resource = EntityModel.of(owner, self, create, update, delete);
            return ResponseEntity.ok(resource);
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
