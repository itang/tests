package testjpa.repository;

import testjpa.domain.LinkDTO;

import java.util.List;

public interface LinkRepository {
    void create(LinkDTO linkDTO);

    List<LinkDTO> findAllByLink();
}
