package milliy.anonymous.milliytravel.repository.custom;

import milliy.anonymous.milliytravel.dto.guide.GuideFilterResultDTO;
import milliy.anonymous.milliytravel.dto.guide.GuideSearchDTO;
import milliy.anonymous.milliytravel.dto.guide.GuideShortInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GuideFilterRepository {

    private final EntityManager entityManager;


    public GuideFilterResultDTO filter(GuideSearchDTO dto, int size) {

        StringBuilder tempSql = new StringBuilder(
                "select distinct(g.id) from GuideEntity g" +
                "  inner join GuideLocationEntity gl on gl.guideId = g.id " +
                "  inner join LocationEntity l on gl.locationId = l.id ");

        Map<String, Object> param = new HashMap<>();

        if (Optional.ofNullable(dto.getKey()).isPresent()) {
            tempSql.append(" where lower(l.district) like :district or l.provence like :provence ");
            param.put("provence", dto.getKey().toUpperCase() + "%");
            param.put("district", dto.getKey().toLowerCase() + "%");
        }


        Query sqlQuery = entityManager.createQuery(tempSql.toString(), String.class);
        param.forEach(sqlQuery::setParameter);

        StringBuilder countSql = new StringBuilder(
//                String countSql =
                "select count(g.id)" +
                        " from GuideEntity as g " +
                        "         inner join  g.profile as p" +
                        "         inner join  g.price as pr " +
                        " where  g.isHiring = true and " +
                        " g.deletedDate is null and p.deletedDate is null " +
                        " and g.id in (:idList) ");

//        Query query = entityManager.createQuery(countSql, GuideShortInfoDTO.class);


        StringBuilder sql = new StringBuilder(
//        String sql =
                "select new milliy.anonymous.milliytravel.dto.guide.GuideShortInfoDTO (g.id as guideId, " +
                        "       p.name        as name," +
                        "       p.surname     as surname," +
                        "       p.photo as photo, " +
                        "       pr.id           as priceId," +
                        "       pr.currency     as price_currency," +
                        "       pr.cost         as price_cost," +
                        "       pr.type         as price_type," +
                        "       g.rate          as rate)" +
                        " from GuideEntity as g " +
                        "         inner join  g.profile as p" +
                        "         inner join  g.price as pr " +
                        " where  g.isHiring = true and " +
                        " g.deletedDate is null and p.deletedDate is null " +
                        " and g.id in (:idList) ");

//        Query query = entityManager.createQuery(sql, GuideShortInfoDTO.class);

        Map<String, Object> params = new HashMap<>();

        params.put("idList", sqlQuery.getResultList());

        if (Optional.ofNullable(dto.getFilterGuide()).isPresent()) {
            if (Optional.ofNullable(dto.getFilterGuide().getMaxPrice()).isPresent() &&
                    Optional.ofNullable(dto.getFilterGuide().getMinPrice()).isPresent()) {
                sql.append(" and pr.cost between :minCost and :maxCost ");
                countSql.append(" and pr.cost between :minCost and :maxCost ");
                params.put("minCost", dto.getFilterGuide().getMinPrice().getCost());
                params.put("maxCost", dto.getFilterGuide().getMaxPrice().getCost());
            } else if (Optional.ofNullable(dto.getFilterGuide().getMaxPrice()).isPresent()) {
                sql.append(" and pr.cost <= :maxCost ");
                countSql.append(" and pr.cost <= :maxCost ");
                params.put("maxCost", dto.getFilterGuide().getMaxPrice().getCost());
            } else if (Optional.ofNullable(dto.getFilterGuide().getMinPrice()).isPresent()) {
                sql.append(" and pr.cost >= :minCost ");
                countSql.append(" and pr.cost >= :minCost ");
                params.put("minCost", dto.getFilterGuide().getMinPrice().getCost());
            }

            if (Optional.ofNullable(dto.getFilterGuide().getMaxRating()).isPresent() &&
                    Optional.ofNullable(dto.getFilterGuide().getMinRating()).isPresent()) {
                sql.append(" and g.rate between  :minRating and :maxRating ");
                countSql.append(" and g.rate between  :minRating and :maxRating ");
                params.put("maxRating", Double.valueOf(dto.getFilterGuide().getMaxRating()));
                params.put("minRating", Double.valueOf(dto.getFilterGuide().getMinRating()));
            } else if (Optional.ofNullable(dto.getFilterGuide().getMaxRating()).isPresent()) {
                sql.append(" and :maxRating <= g.rate ");
                countSql.append(" and :maxRating <= g.rate ");
                params.put("maxRating", Double.valueOf(dto.getFilterGuide().getMaxRating()));
            } else if (Optional.ofNullable(dto.getFilterGuide().getMinRating()).isPresent()) {
                sql.append(" and :minRating >= g.rate ");
                countSql.append(" and :minRating >= g.rate ");
                params.put("minRating", Double.valueOf(dto.getFilterGuide().getMinRating()));
            }

            if (Optional.ofNullable(dto.getFilterGuide().getGender()).isPresent()) {
                switch (dto.getFilterGuide().getGender()){
                    case FEMALE -> {
                        sql.append(" and p.gender = 'FEMALE' ");
                        countSql.append(" and p.gender = 'FEMALE' ");
                    }
                    case MALE -> {
                        sql.append(" and p.gender = 'MALE' ");
                        countSql.append(" and p.gender = 'MALE' ");
                    }
                }
            }
        }

//        sql.append(" :pageable ");
//        params.put("pageable", PageRequest.of(size, 15));

        Query query = entityManager.createQuery(sql.toString(), GuideShortInfoDTO.class);
        Query countQuery = entityManager.createQuery(countSql.toString(), Long.class);

        query.setFirstResult(size);
        query.setMaxResults(size + 15);

        params.forEach(query::setParameter);
        params.forEach(countQuery::setParameter);

        return new GuideFilterResultDTO(query.getResultList(), (Long) countQuery.getResultList().get(0));
    }
}
