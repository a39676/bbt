package demo.clawing.lottery.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LotterySixSoldDetail {
    private Long id;

    private LocalDateTime recordTime;

    private String recordOrgId;

    private BigDecimal soldAmount;

    private BigDecimal prizePoolAmount;

    private BigDecimal prize1Amount;

    private Integer prize1Count;

    private BigDecimal prize2Amount;

    private Integer prize2Count;

    private BigDecimal prize3Amount;

    private Integer prize3Count;

    private Boolean isDelete;

    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
    }

    public String getRecordOrgId() {
        return recordOrgId;
    }

    public void setRecordOrgId(String recordOrgId) {
        this.recordOrgId = recordOrgId == null ? null : recordOrgId.trim();
    }

    public BigDecimal getSoldAmount() {
        return soldAmount;
    }

    public void setSoldAmount(BigDecimal soldAmount) {
        this.soldAmount = soldAmount;
    }

    public BigDecimal getPrizePoolAmount() {
        return prizePoolAmount;
    }

    public void setPrizePoolAmount(BigDecimal prizePoolAmount) {
        this.prizePoolAmount = prizePoolAmount;
    }

    public BigDecimal getPrize1Amount() {
        return prize1Amount;
    }

    public void setPrize1Amount(BigDecimal prize1Amount) {
        this.prize1Amount = prize1Amount;
    }

    public Integer getPrize1Count() {
        return prize1Count;
    }

    public void setPrize1Count(Integer prize1Count) {
        this.prize1Count = prize1Count;
    }

    public BigDecimal getPrize2Amount() {
        return prize2Amount;
    }

    public void setPrize2Amount(BigDecimal prize2Amount) {
        this.prize2Amount = prize2Amount;
    }

    public Integer getPrize2Count() {
        return prize2Count;
    }

    public void setPrize2Count(Integer prize2Count) {
        this.prize2Count = prize2Count;
    }

    public BigDecimal getPrize3Amount() {
        return prize3Amount;
    }

    public void setPrize3Amount(BigDecimal prize3Amount) {
        this.prize3Amount = prize3Amount;
    }

    public Integer getPrize3Count() {
        return prize3Count;
    }

    public void setPrize3Count(Integer prize3Count) {
        this.prize3Count = prize3Count;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}