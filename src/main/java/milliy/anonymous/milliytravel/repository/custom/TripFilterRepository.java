package milliy.anonymous.milliytravel.repository.custom;

import milliy.anonymous.milliytravel.dto.detail.PriceDTO;
import milliy.anonymous.milliytravel.dto.request.TripFilterDTO;
import milliy.anonymous.milliytravel.dto.trip.SearchTripDTO;
import milliy.anonymous.milliytravel.dto.trip.TripFilterResultDTO;
import milliy.anonymous.milliytravel.dto.trip.TripShortInfoDTO;
import lombok.RequiredArgsConstructor;
import milliy.anonymous.milliytravel.mapper.TripFilterInfoMapper;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TripFilterRepository {

    private final EntityManager entityManager;

    public TripFilterResultDTO filter(SearchTripDTO filter, int size) {

        StringBuilder s = new StringBuilder(
//        String s =
                "select distinct(t.id) from TripEntity t" +
                        " inner join t.guide g " +
                        " inner join g.profile p " +
                        "  inner join TripLocationEntity tl on tl.tripId = t.id " +
                        "  inner join LocationEntity l on tl.locationId = l.id " +
                        "  where p.deletedDate is null and g.deletedDate is null  ");

//        Query sqlQuery = entityManager.createQuery(s, String.class);

        Map<String, Object> param = new HashMap<>();

        if (Optional.ofNullable(filter.getKey()).isPresent()) {
            s.append(" and lower(l.district) like :district or l.provence like :provence ");
            param.put("provence", filter.getKey().toUpperCase() + "%");
            param.put("district", filter.getKey().toLowerCase() + "%");
        }


        Query sqlQuery = entityManager.createQuery(s.toString(), String.class);
        param.forEach(sqlQuery::setParameter);

        StringBuilder countSql = new StringBuilder(
//        String sql=
                "select  count(t.id) "+
                        " from TripEntity t" +
                        " inner join t.guide g " +
                        " inner join g.profile pr " +
                        " inner join PriceEntity p on p.id = t.priceId " +
                        " and t.deletedDate is null and g.deletedDate is null " +
                        " and pr.deletedDate is null " +
                        " and t.id in (:tripIdList) ");




        StringBuilder sql = new StringBuilder(
//        String sql=
                "select new milliy.anonymous.milliytravel.dto.trip.TripShortInfoDTO( t.id as id, t.name as name," +
                        " t.rate as rate, " +
                        " p.id as priceId, p.currency as currency, p.cost as cost,p.type as tourType) " +
                        " from TripEntity t" +
                        " inner join t.guide g " +
                        " inner join g.profile pr " +
                        " inner join PriceEntity p on p.id = t.priceId " +
                        " and t.deletedDate is null and g.deletedDate is null " +
                        " and pr.deletedDate is null " +
                        " and t.id in (:tripIdList) ");

//        Query query = entityManager.createQuery(sql, TripFilterInfoMapper.class);

        Map<String, Object> params = new HashMap<>();

        TripFilterDTO dto = filter.getFilterTrip();

        params.put("tripIdList", sqlQuery.getResultList());

        if (Optional.ofNullable(dto).isPresent()) {

            PriceDTO maxPrice = dto.getMaxPrice();
            PriceDTO minPrice = dto.getMinPrice();


            if (Optional.ofNullable(maxPrice).isPresent() &&
                    Optional.ofNullable(minPrice).isPresent()) {

                sql.append("and p.cost between :minCost and :maxCost ");
                countSql.append("and p.cost between :minCost and :maxCost ");
                params.put("minCost", minPrice.getCost());
                params.put("maxCost", maxPrice.getCost());

                if (Optional.ofNullable(maxPrice.getType()).isPresent()) {
                    sql.append("and p.type = :type ");
                    countSql.append("and p.type = :type ");
                    params.put("type", maxPrice.getType());
                } else if (Optional.ofNullable(minPrice.getType()).isPresent()) {
                    sql.append("and p.type = :type ");
                    countSql.append("and p.type = :type ");
                    params.put("type", minPrice.getType());
                }

                if (Optional.ofNullable(maxPrice.getCurrency()).isPresent()) {
                    sql.append("and p.currency = :currency ");
                    countSql.append("and p.currency = :currency ");
                    params.put("currency", maxPrice.getCurrency());
                } else if (Optional.ofNullable(minPrice.getCurrency()).isPresent()) {
                    sql.append("and p.currency = :currency ");
                    countSql.append("and p.currency = :currency ");
                    params.put("currency", minPrice.getCurrency());
                }

            } else if (Optional.ofNullable(maxPrice).isPresent()) {
                sql.append("and p.cost <= :maxCost ");
                countSql.append("and p.cost <= :maxCost ");
                sql.append("and p.type = :type ");
                countSql.append("and p.type = :type ");
                sql.append("and p.currency = :currency ");
                countSql.append("and p.currency = :currency ");
                params.put("currency", maxPrice.getCurrency());
                params.put("type", maxPrice.getType());
                params.put("maxCost", maxPrice.getCost());
            } else if (Optional.ofNullable(minPrice).isPresent()) {
                sql.append("and p.cost >= :minCost ");
                countSql.append("and p.cost >= :minCost ");
                sql.append("and p.type = :type ");
                countSql.append("and p.type = :type ");
                sql.append("and p.currency = :currency ");
                countSql.append("and p.currency = :currency ");
                params.put("currency", minPrice.getCurrency());
                params.put("type", minPrice.getType());
                params.put("minCost", minPrice.getCost());
            }

            if (Optional.ofNullable(dto.getMaxPeople()).isPresent() &&
                    Optional.ofNullable(dto.getMinPeople()).isPresent()) {
                sql.append("and :minPeople between t.minPeople and t.maxPeople " +
                        "and :maxPeople between t.minPeople and t.maxPeople ");
                countSql.append("and :minPeople between t.minPeople and t.maxPeople " +
                        "and :maxPeople between t.minPeople and t.maxPeople ");
                params.put("maxPeople", dto.getMaxPeople());
                params.put("minPeople", dto.getMinPeople());
            } else if (Optional.ofNullable(dto.getMaxPeople()).isPresent()) {
                sql.append("and :maxPeople <= t.maxPeople ");
                countSql.append("and :maxPeople <= t.maxPeople ");
                params.put("maxPeople", dto.getMaxPeople());
            } else if (Optional.ofNullable(dto.getMinPeople()).isPresent()) {
                sql.append("and :minPeople >= t.minPeople ");
                countSql.append("and :minPeople >= t.minPeople ");
                params.put("minPeople", dto.getMinPeople());
            }

            if (Optional.ofNullable(dto.getMaxRate()).isPresent() &&
                    Optional.ofNullable(dto.getMinRate()).isPresent()) {
                sql.append("and t.rate between :minRate and :maxRate ");
                countSql.append("and t.rate between :minRate and :maxRate ");
                params.put("maxRate", Double.valueOf(dto.getMaxRate()));
                params.put("minRate", Double.valueOf(dto.getMinRate()));
            } else if (Optional.ofNullable(dto.getMaxRate()).isPresent()) {
                sql.append("and t.rate <= :maxRate ");
                countSql.append("and t.rate <= :maxRate ");
                params.put("maxRate", Double.valueOf(dto.getMaxRate()));
            } else if (Optional.ofNullable(dto.getMinRate()).isPresent()) {
                sql.append("and t.rate >= :minRate ");
                countSql.append("and t.rate >= :minRate ");
                params.put("minRate", Double.valueOf(dto.getMinRate()));
            }

            if (Optional.ofNullable(dto.getDays()).isPresent()) {
                sql.append("and t.days = :days ");
                countSql.append("and t.days = :days ");
                params.put("days", dto.getDays());
            }
        }


        Query query = entityManager.createQuery(sql.toString(), TripShortInfoDTO.class);
        Query countQuery = entityManager.createQuery(countSql.toString(), Long.class);

        query.setFirstResult(size);
        query.setMaxResults(size + 15);

        params.forEach(query::setParameter);
        params.forEach(countQuery::setParameter);




        return new TripFilterResultDTO(query.getResultList(),(Long) countQuery.getResultList().get(0));
    }

}
