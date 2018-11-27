package testjpa.repository.impl;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import testjpa.domain.LinkDTO;
import testjpa.repository.LinkRepository;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

//@Repository
@Singleton
@Named("db_sql2o")
public class LinkRepositorySql2oImpl implements LinkRepository {

    private final Sql2o sql2o;

    public LinkRepositorySql2oImpl(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void create(LinkDTO linkDTO) {
        String sql = "insert into links(id, link) value(:id, :link)";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .bind(linkDTO)
                    .executeUpdate();
        }
    }

    @Override
    public List<LinkDTO> findAllByLink() {
        String sql = "SELECT * FROM links";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(LinkDTO.class);
        }
    }
}
