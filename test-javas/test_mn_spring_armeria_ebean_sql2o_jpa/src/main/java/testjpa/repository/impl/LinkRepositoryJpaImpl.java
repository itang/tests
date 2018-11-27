package testjpa.repository.impl;

import testjpa.domain.LinkDTO;
import testjpa.entity.LinkDT;
import testjpa.repository.LinkRepository;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Named("db_jpa")
public class LinkRepositoryJpaImpl implements LinkRepository {
    @PersistenceContext
    private EntityManager entityManager;

    // public LinkRepositoryJpaImpl(@CurrentSession EntityManager entityManager) {
    //    this.entityManager = entityManager;
    // }

    @Override
    public void create(LinkDTO linkDTO) {
        LinkDT dt = new LinkDT();
        dt.setId(null);
        dt.setLink(linkDTO.getLink());
        entityManager.persist(dt);
    }

    @Override
    public List<LinkDTO> findAllByLink() {
        List<LinkDT> list = entityManager.createQuery("select c from LinkDT c").getResultList();
        return list.stream().map(it -> new LinkDTO(it.getId(), it.getLink())).collect(Collectors.toList());
    }
}
