package demo.finance.cryptoCoin.data.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoCoinMaxVolume extends CryptoCoinMaxVolumeKey {
    private BigDecimal maxVolume;

    private LocalDateTime maxVolumeTime;

    private BigDecimal maxAvg5Volume;

    private LocalDateTime maxAvg5VolumeTime;

    private BigDecimal maxAvg10Volume;

    private LocalDateTime maxAvg10VolumeTime;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public BigDecimal getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(BigDecimal maxVolume) {
        this.maxVolume = maxVolume;
    }

    public LocalDateTime getMaxVolumeTime() {
        return maxVolumeTime;
    }

    public void setMaxVolumeTime(LocalDateTime maxVolumeTime) {
        this.maxVolumeTime = maxVolumeTime;
    }

    public BigDecimal getMaxAvg5Volume() {
        return maxAvg5Volume;
    }

    public void setMaxAvg5Volume(BigDecimal maxAvg5Volume) {
        this.maxAvg5Volume = maxAvg5Volume;
    }

    public LocalDateTime getMaxAvg5VolumeTime() {
        return maxAvg5VolumeTime;
    }

    public void setMaxAvg5VolumeTime(LocalDateTime maxAvg5VolumeTime) {
        this.maxAvg5VolumeTime = maxAvg5VolumeTime;
    }

    public BigDecimal getMaxAvg10Volume() {
        return maxAvg10Volume;
    }

    public void setMaxAvg10Volume(BigDecimal maxAvg10Volume) {
        this.maxAvg10Volume = maxAvg10Volume;
    }

    public LocalDateTime getMaxAvg10VolumeTime() {
        return maxAvg10VolumeTime;
    }

    public void setMaxAvg10VolumeTime(LocalDateTime maxAvg10VolumeTime) {
        this.maxAvg10VolumeTime = maxAvg10VolumeTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}