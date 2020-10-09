package vn.nextpay.demo_spingboot_mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Version;

import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseData {

    private String owner;

    @Version
    private Long version;

    @CreatedDate
    private Date createdAt;
    private String createdBy;
    private ZonedDateTime updateAt;
    private String updateBy;
}
