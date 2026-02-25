package co.com.tintolab.Repository;

import co.com.tintolab.Dto.ReportMonthResponseDTO;
import co.com.tintolab.Dto.ReportMonthSalesResponseDTO;
import co.com.tintolab.Models.SaleModel;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<SaleModel, Long> {
    Optional<SaleModel> findByCode(String code);
    boolean existsByCode(String code);
    List<SaleModel> findByDate_hourBetween(LocalDateTime init, LocalDateTime finish);
    @Query("""
       SELECT new co.com.tintolab.Dto.ReportMonthResponseDTO(
           COALESCE(SUM(v.total), 0),
           COUNT(v),
           :year,
           :month
       )
       FROM SaleModel v
       WHERE YEAR(v.date_hour) = :year\s
       AND MONTH(v.date_hour) = :month
      \s""")
    ReportMonthResponseDTO getReportMounth(@Param("year") int year,
                                           @Param("month") int month);

    @Query("""
           SELECT new co.com.tintolab.Dto.ReportMonthSalesResponseDTO(
               v.code,
               v.dateHour,
               v.total,
               v.payMethod
           )
           FROM SaleModel v
           WHERE YEAR(v.date_hour) = :year 
           AND MONTH(v.date_hour) = :month
           ORDER BY v.date_hour DESC
           """)
    List<ReportMonthSalesResponseDTO> getSalesMonthDetails(int year, int month);
}
