package ru.asvronsky.scrapper.repository;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.model.Link;

@Repository
@RequiredArgsConstructor
public class JdbcLinkDao implements LinkDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Link> rowMapper = new DataClassRowMapper<>(Link.class);

    @Override
    @Transactional
    public List<Link> findOutdated(Duration offset) {
        String sqlFindOutdated = """
                select id, url from link
                where updated_at < :cutoff
                """;
        return jdbcTemplate.query(
            sqlFindOutdated,
            Map.of("cutoff", OffsetDateTime.now().minus(offset)),
            rowMapper
        );
    }

    @Override
    @Transactional
    public List<String> update(Link link) {
        String sqlUpdateAndGetDiff = """
                with diff as (
                    update link x
                    set 
                        website_data = cast(:websiteData as jsonb),
                        updated_at = now()
                    from (select website_data from link where id = :id for update) as y
                    where id = :id
                    returning (
                        select array_agg(new.key)
                        from jsonb_each(x.website_data) new
                            left join jsonb_each(y.website_data) old on new.key = old.key
                        where 
                            new.value is distinct from old.value
                    ) as diff
                )
                select unnest(diff) from diff
                """;
        return jdbcTemplate.queryForList(
            sqlUpdateAndGetDiff,
            new BeanPropertySqlParameterSource(link),
            String.class
        );
    }
    
}
