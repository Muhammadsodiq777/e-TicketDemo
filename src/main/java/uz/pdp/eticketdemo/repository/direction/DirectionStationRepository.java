package uz.pdp.eticketdemo.repository.direction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.eticketdemo.model.dto.direction.DirectionBetweenStationsDto;
import uz.pdp.eticketdemo.model.entity.direction.DirectionStationEntity;

import java.util.List;

public interface DirectionStationRepository extends JpaRepository<DirectionStationEntity, Long> {

    @Query(value = "select d from DirectionStationEntity d where d.direction_id = ?1 order by d.stationOrder", nativeQuery = true)
    List<DirectionStationEntity> getDirectionStationEntitiesByDirectionIdOrderByStationOrder(Long directionId);

    @Modifying
    @Query(value = "update direction_station set station_order = station_order + 1 where direction_id = ?1 and station_order >= ?2", nativeQuery = true)
    boolean updateStationOrder(long directionId, int stationOrder);

    @Query(value = "select d.direction_id as direction_id, d.station_id as from_station_id, d.station_order as from_station_order, " +
            " s.station_id as to_station_id, s.station_order as to_station_order from direction_station d inner join direction_station s on d.direction_id=s.direction_id where d.station_order > s.station_order and d.station_id = ?1 and s.station_id = ?2", nativeQuery = true)
    List<DirectionBetweenStationsDto> getDirectionStationEntitiesByTwoStations(Long fromStationId, Long toStationId);

    DirectionStationEntity getDirectionStationEntityByStationIdAndDirectionId(Long stationId, Long DirectionId);

    @Query(value = "select count (station_id) from direction_station where direction_id = ?1", nativeQuery = true)
    Integer getNumberOfStationForDirection(Long directionId);
}
