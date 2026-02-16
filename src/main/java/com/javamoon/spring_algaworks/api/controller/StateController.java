package com.javamoon.spring_algaworks.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javamoon.spring_algaworks.domain.exception.StateNotFoundException;
import com.javamoon.spring_algaworks.domain.model.State;
import com.javamoon.spring_algaworks.domain.repository.StateRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/state")
public class StateController {

    @Autowired
    private StateRepository stateRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<State>> listAll() {
        List<State> states = stateRepository.findAll();
        return ResponseEntity.ok(states);
    }

    @PostMapping
    public ResponseEntity<State> save(@RequestBody @Valid State state) {
        State stateDb = stateRepository.save(state);
        return ResponseEntity.status(HttpStatus.CREATED).body(stateDb);
    }

    @PatchMapping("/{stateId}")
    public ResponseEntity<State> edit(@PathVariable Long stateId, @RequestBody State state) {
        Optional<State> stateDb = stateRepository.findById(stateId);
        
        if (stateDb.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(state, stateDb.get(), "id");
        
        stateRepository.save(stateDb.get());

        return ResponseEntity.ok(stateDb.get());
    }

    @DeleteMapping("/{stateId}")
    public void remove(@PathVariable Long stateId) {
        Optional<State> state = stateRepository.findById(stateId);

        if (state.isEmpty()) {
            throw new StateNotFoundException(stateId);
        }

        stateRepository.deleteById(stateId);
    }
}
