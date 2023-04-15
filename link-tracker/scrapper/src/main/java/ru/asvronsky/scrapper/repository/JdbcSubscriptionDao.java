package ru.asvronsky.scrapper.repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.model.Chat;
import ru.asvronsky.scrapper.model.Link;

@Repository
@RequiredArgsConstructor
public class JdbcSubscriptionDao implements SubscriptionDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    // private final TransactionTemplate transactionTemplate;

    private final RowMapper<Link> rowMapper = new DataClassRowMapper<>(Link.class);

    @Override
    @Transactional
    public Link add(Chat chat, Link link) {
        String sqlAddLink = """
                insert into link(url)
                values (:url)
                on conflict (url) do nothing
                returning *
                """;
        String sqlGetLinkId  = """
                select * from link where url = :url
                """;
        String sqlSubscribe = """
                insert into subscription(chat_id, link_id)
                values (:chatId, :linkId)
                """;
        
        Link returnedLink =  Objects.requireNonNullElse(
            jdbcTemplate.queryForObject(
                sqlAddLink,
                new BeanPropertySqlParameterSource(link),
                rowMapper
            ), 
            jdbcTemplate.queryForObject(
                sqlGetLinkId,
                new BeanPropertySqlParameterSource(link),
                rowMapper
            )
        );

        jdbcTemplate.update(
            sqlSubscribe,
            Map.of("chatId", chat.getChatId(), "linkId", returnedLink.getId())
        );

        return returnedLink;
    }

    @Override
    @Transactional
    public Optional<Link> remove(Chat chat, Link link) {
        String sqlDelete = """
                delete from subscription as s
                using link as l
                where l.id = s.link_id and l.url = :url and s.chat_id = :chatId
                returning l.*
                """;
        
        return Optional.ofNullable(jdbcTemplate.queryForObject(
            sqlDelete,
            Map.of("url", link.getUrl(), "chatId", chat.getChatId()),
            rowMapper
        ));
    }

    @Override
    @Transactional
    public List<Link> findAll(Chat chat) {
        String sqlFindAll = """
                select l.* from link as l, subscription as s
                where l.id = s.link_id and s.chat_id = :chatId
                """;
        return jdbcTemplate.query(
            sqlFindAll,
            Map.of("chatId", chat.getChatId()),
            rowMapper
        );
    }
    
}
