package demo.finance.cryptoCoin.data.pojo.po;

import java.time.LocalDateTime;

public class CryptoCoinCatalog {
    private Long id;

    private String coinNameEnShort;

    private String coinNameEn;

    private String coinNameCn;

    private LocalDateTime createtime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoinNameEnShort() {
        return coinNameEnShort;
    }

    public void setCoinNameEnShort(String coinNameEnShort) {
        this.coinNameEnShort = coinNameEnShort == null ? null : coinNameEnShort.trim();
    }

    public String getCoinNameEn() {
        return coinNameEn;
    }

    public void setCoinNameEn(String coinNameEn) {
        this.coinNameEn = coinNameEn == null ? null : coinNameEn.trim();
    }

    public String getCoinNameCn() {
        return coinNameCn;
    }

    public void setCoinNameCn(String coinNameCn) {
        this.coinNameCn = coinNameCn == null ? null : coinNameCn.trim();
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}