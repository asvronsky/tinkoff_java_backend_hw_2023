package ru.asvronsky.scrapper.model.jpa;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "chatId", callSuper = false)
@Table(name = "chat")
public class ChatEntity {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @ManyToMany(mappedBy = "subscribers", fetch = FetchType.LAZY)
    private Set<LinkEntity> subscriptions;
}
