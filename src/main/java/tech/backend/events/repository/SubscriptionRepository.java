package tech.backend.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.backend.events.dto.SubscriptionRankingItem;
import tech.backend.events.model.Event;
import tech.backend.events.model.Subscription;
import tech.backend.events.model.User;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    public Subscription findByEventAndSubscriber(Event event, User user);

    @Query(value = "select count(subscription_number) as quantidade, indication_user_id, user_name"
            + "from tb_subscription \n" +
            "inner join tb_user \n" +
            "    on tb_subscription.indication_user_id = tb_user.user_id\n" +
            "where indication_user_id is not null\n" +
            "    and event_id = :eventId" +
            "group by indication_user_id\n" +
            "order by quantidade desc", nativeQuery = true)
    public List<SubscriptionRankingItem> generateRanking(@Param("eventId") Integer eventId);
}
