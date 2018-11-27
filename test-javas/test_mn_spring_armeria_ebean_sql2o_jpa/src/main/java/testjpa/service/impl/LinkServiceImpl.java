package testjpa.service.impl;

import com.google.common.base.Strings;
import io.micronaut.spring.tx.annotation.Transactional;
import org.springframework.stereotype.Service;
import testjpa.domain.LinkDTO;
import testjpa.repository.LinkRepository;
import testjpa.service.LinkService;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

//@Service
@Singleton
public class LinkServiceImpl implements LinkService {
    private final LinkRepository linkRepository;

    public LinkServiceImpl(@Named("db_jpa") LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Transactional(readOnly = false)
    @Override
    public String addLink(String link) {
        String id = UUID.randomUUID().toString();
        linkRepository.create(new LinkDTO(id, link));
        linkRepository.create(new LinkDTO(UUID.randomUUID().toString(), link + Strings.repeat("*", 180)));
        return id;
    }

    @Transactional(readOnly = true)
    @Override
    public List<LinkDTO> findLinksByLink(String searchLink) {
        return linkRepository.findAllByLink();
    }
}
