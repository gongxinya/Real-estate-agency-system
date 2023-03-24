package stacs.estate.cs5031p3code.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Flat entity.
 *
 * @author 220032952
 * @version 0.0.1
 */
@TableName(value = "flat")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flat implements Serializable {
    /**
     * The id of flat
     */
    @TableId(type = IdType.AUTO)
    private Long flatId;

    /**
     * The name of flat
     */
    private String flatName;

    /**
     * The area of flat
     */
    private BigDecimal flatArea;

    /**
     * The sold out date of flat
     */
    private Date flatSoldOutDate;

    /**
     * The price of flat
     */
    private BigDecimal flatPrice;

    /**
     * The id of building
     */
    private Long buildingId;

    /**
     * The id of user
     */
    private Long userId;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Flat other = (Flat) that;
        return (this.getFlatId() == null ? other.getFlatId() == null : this.getFlatId().equals(other.getFlatId()))
                && (this.getFlatName() == null ? other.getFlatName() == null : this.getFlatName().equals(other.getFlatName()))
                && (this.getFlatArea() == null ? other.getFlatArea() == null : this.getFlatArea().equals(other.getFlatArea()))
                && (this.getFlatSoldOutDate() == null ? other.getFlatSoldOutDate() == null : this.getFlatSoldOutDate().equals(other.getFlatSoldOutDate()))
                && (this.getFlatPrice() == null ? other.getFlatPrice() == null : this.getFlatPrice().equals(other.getFlatPrice()))
                && (this.getBuildingId() == null ? other.getBuildingId() == null : this.getBuildingId().equals(other.getBuildingId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFlatId() == null) ? 0 : getFlatId().hashCode());
        result = prime * result + ((getFlatName() == null) ? 0 : getFlatName().hashCode());
        result = prime * result + ((getFlatArea() == null) ? 0 : getFlatArea().hashCode());
        result = prime * result + ((getFlatSoldOutDate() == null) ? 0 : getFlatSoldOutDate().hashCode());
        result = prime * result + ((getFlatPrice() == null) ? 0 : getFlatPrice().hashCode());
        result = prime * result + ((getBuildingId() == null) ? 0 : getBuildingId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", flatId=").append(flatId);
        sb.append(", flatName=").append(flatName);
        sb.append(", flatArea=").append(flatArea);
        sb.append(", flatSoldOutDate=").append(flatSoldOutDate);
        sb.append(", flatPrice=").append(flatPrice);
        sb.append(", buildingId=").append(buildingId);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}