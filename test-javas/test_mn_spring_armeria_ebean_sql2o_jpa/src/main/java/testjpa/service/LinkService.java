package testjpa.service;

import testjpa.domain.LinkDTO;

import java.util.List;

public interface LinkService {
    String addLink(String link);

    List<LinkDTO> findLinksByLink(String searchLink);
}
