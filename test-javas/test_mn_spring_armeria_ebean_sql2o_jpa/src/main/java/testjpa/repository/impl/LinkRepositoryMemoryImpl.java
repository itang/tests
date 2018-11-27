package testjpa.repository.impl;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Repository;
import testjpa.domain.LinkDTO;
import testjpa.repository.LinkRepository;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
@Named("memory")
public class LinkRepositoryMemoryImpl implements LinkRepository {
    private LinkedHashMap<String, LinkDTO> storge = new LinkedHashMap<>();

    @Override
    public void create(LinkDTO linkDTO) {
        Preconditions.checkArgument(linkDTO.getLink() != null, "id should be wired");

        storge.put(linkDTO.getId(), linkDTO);
    }

    @Override
    public List<LinkDTO> findAllByLink() {
        return new ArrayList<>(storge.values());
    }
}
