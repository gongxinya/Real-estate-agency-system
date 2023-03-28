package stacs.estate.cs5031p3code.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Building entity.
 *
 * @author 220032952
 * @version 0.0.1
 */
@TableName(value = "building")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Building implements Serializable {
    /**
     * The id of building
     */
    @TableId(type = IdType.AUTO)
    private Long buildingId;

    /**
     * The name of building
     */
    private String buildingName;

    /**
     * The address of building
     */
    private String buildingAddress;

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
        Building other = (Building) that;
        return (this.getBuildingId() == null ? other.getBuildingId() == null : this.getBuildingId().equals(other.getBuildingId()))
                && (this.getBuildingName() == null ? other.getBuildingName() == null : this.getBuildingName().equals(other.getBuildingName()))
                && (this.getBuildingAddress() == null ? other.getBuildingAddress() == null : this.getBuildingAddress().equals(other.getBuildingAddress()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBuildingId() == null) ? 0 : getBuildingId().hashCode());
        result = prime * result + ((getBuildingName() == null) ? 0 : getBuildingName().hashCode());
        result = prime * result + ((getBuildingAddress() == null) ? 0 : getBuildingAddress().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", buildingId=").append(buildingId);
        sb.append(", buildingName=").append(buildingName);
        sb.append(", buildingAddress=").append(buildingAddress);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}