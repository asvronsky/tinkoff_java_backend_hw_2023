package ru.asvronsky.scrapper.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.model.Chat;

@Repository
@RequiredArgsConstructor
public class JdbcChatDao implements ChatDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Chat> rowMapper = new DataClassRowMapper<>(Chat.class);

    @Override
    @Transactional
    public void add(long chatId) {
        String sqlAdd = """
                insert into chat(chat_id)
                values (:chatId)
                on conflict do nothing
                """;
        
        jdbcTemplate.update(
            sqlAdd,
            Map.of("chatId", chatId)
        );
    }

    @Override
    @Transactional
    public Optional<Chat> remove(long chatId) {
        String sqlDelete = """
                delete from chat
                where chat_id = :chatId
                returning *
                """;
        
        return Optional.ofNullable(jdbcTemplate.queryForObject(
            sqlDelete,
            Map.of("chatId", chatId),
            rowMapper
        ));
    }

    @Override
    @Transactional
    public List<Chat> findAll() {
        String sqlFindAll = """
                select * from chat
                """;
        return jdbcTemplate.query(
            sqlFindAll,
            rowMapper
        );
    }
    
}
