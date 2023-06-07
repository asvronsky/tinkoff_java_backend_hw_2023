package ru.asvronsky.scrapper.repository.jdbc;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.model.Link;
import ru.asvronsky.scrapper.repository.SubscriptionDao;

@RequiredArgsConstructor
public class JdbcSubscriptionDao implements SubscriptionDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Link> rowMapper = new DataClassRowMapper<>(Link.class);

    @Override
    @Transactional
    public Link add(long chatId, Link link) {
        String sqlAddLink = """
                with new_link as (
                    insert into link(url)
                    values (:url)
                    on conflict (url) do nothing
                    returning id, url
                ) 
                select id, url from new_link
                union all
                select id, url from link where url = :url
                """;

        String sqlSubscribe = """
                insert into subscription(chat_id, link_id)
                values (:chatId, :linkId)
                """;
        
        Link returnedLink = jdbcTemplate.queryForObject(
                sqlAddLink,
                new BeanPropertySqlParameterSource(link),
                rowMapper
            );

        jdbcTemplate.update(
            sqlSubscribe,
            Map.of("chatId", chatId, "linkId", Optional.of(returnedLink).get().getId())
        );

        return returnedLink;
    }

    @Override
    @Transactional
    public Optional<Link> remove(long chatId, Link link) {
        String sqlDelete = """
                delete from subscription as s
                using link as l
                where l.id = s.link_id and l.url = :url and s.chat_id = :chatId
                returning l.id, l.url
                """;
        
        return Optional.ofNullable(jdbcTemplate.queryForObject(
            sqlDelete,
            Map.of("url", link.getUrl(), "chatId", chatId),
            rowMapper
        ));
    }

    @Override
    @Transactional
    public List<Link> findLinksByChat(long chatId) {
        String sqlFindLinks = """
                select l.id, l.url from link as l, subscription as s
                where l.id = s.link_id and s.chat_id = :chatId
                """;
        
        return jdbcTemplate.query(
            sqlFindLinks,
            Map.of("chatId", chatId),
            rowMapper
        );
    }

    @Override
    public List<Long> findChatsByLink(Link link) {
        String sqlFindChatsByUrl = """
                select chat_id
                from subscription s, link l
                where l.id = s.link_id and l.url = :url
                """;
        
        String sqlFindChatsById = """
            select chat_id
            from subscription s
            where link_id = :id
            """;
        String useSql = (link.getId() == null) ? sqlFindChatsByUrl : sqlFindChatsById;

        return jdbcTemplate.queryForList(
            useSql,
            new BeanPropertySqlParameterSource(link),
            Long.class
        );
    }
    
}
