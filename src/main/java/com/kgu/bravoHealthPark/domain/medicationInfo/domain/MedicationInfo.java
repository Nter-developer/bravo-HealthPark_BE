package com.kgu.bravoHealthPark.domain.medicationInfo.domain;

import com.kgu.bravoHealthPark.domain.state.domain.State;
import com.kgu.bravoHealthPark.domain.type.domain.Type;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MedicationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="medInfo_id")
    private Long medInfoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private State state;

    private String entpName; // 기업명
    private String itemName; // 제품명
    private int tablet; // 용량
    private int days;
    private LocalDate startDate;
    private LocalDate endDate;
    private String memo;

}