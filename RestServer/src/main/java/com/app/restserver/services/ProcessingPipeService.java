package com.app.restserver.services;

import com.app.restserver.endpoints.requests.PipeCreationRequest;
import com.app.restserver.endpoints.requests.PipeUpdateRequest;
import com.app.restserver.endpoints.responses.PipeResponse;
import com.app.restserver.entities.Module;
import com.app.restserver.entities.Pipe;
import com.app.restserver.entities.User;
import com.app.restserver.persistance.core.ModuleRepository;
import com.app.restserver.persistance.core.PipeRepository;
import com.app.restserver.persistance.core.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProcessingPipeService {
    private final UserRepository userRepository;
    private final PipeRepository pipeRepository;
    private final ModuleRepository moduleRepository;

    @Autowired
    public ProcessingPipeService(
            UserRepository userRepository,
            PipeRepository pipeRepository,
            ModuleRepository moduleRepository
    ) {
        this.userRepository = userRepository;
        this.pipeRepository = pipeRepository;
        this.moduleRepository = moduleRepository;
    }

    @Transactional
    public PipeResponse create(Long userId, PipeCreationRequest request) {
        User user = userRepository.findByIdIfExists(userId);
        List<Module> modules  = moduleRepository.findAllModulesOrderedByDefinitionId(request.getModuleIds());
        String pipeName = user.getUsername() + "/" + request.getName();
        Pipe pipe = new Pipe(user, pipeName, request.getIsPublic(), modules);
        return PipeResponse.fromEntity(pipeRepository.save(pipe));
    }

    public List<PipeResponse> getAll(Long userId) {
        List<Pipe> pipes = pipeRepository.findAllCreatedByUserOrPublicPipes(userId);
        pipes.forEach(pipe -> pipe.setModules(moduleRepository.findPipeDefinitionModules(pipe.getId())));
        return pipes.stream()
                .map(PipeResponse::fromEntity)
                .toList();
    }

    public PipeResponse getById(Long pipeId) {
        Pipe pipe = pipeRepository.findByIdIfExists(pipeId);
        pipe.setModules(moduleRepository.findPipeDefinitionModules(pipeId));
        return PipeResponse.fromEntity(pipe);
    }

    @Transactional
    public PipeResponse updatePipeDefinition(PipeUpdateRequest pipeUpdateRequest, Long userId) {
        checkOwnershipForPipe(userId, pipeUpdateRequest.getPipeId());
        pipeRepository.updatePipeDefinition(pipeUpdateRequest.getPipeId(), pipeUpdateRequest.getIsPublic(), pipeUpdateRequest.getModuleIds());
        Pipe pipe = pipeRepository.findByIdIfExists(pipeUpdateRequest.getPipeId());
        return PipeResponse.fromEntity(pipe);
    }

    @Transactional
    public void remove(Long userId, Long pipeId) {
        checkOwnershipForPipe(userId, pipeId);
        pipeRepository.deleteById(pipeId);
    }

    private void checkOwnershipForPipe(Long userId, Long pipeId) {
        Long pipeOwnerId = pipeRepository.findOwnerIdForPipe(pipeId);

        if (!userId.equals(pipeOwnerId))
            throw new NotAuthorizedException(String.format("Cannot alter pipe:%d as user:%d is not the owner", pipeId, userId));
    }
}
