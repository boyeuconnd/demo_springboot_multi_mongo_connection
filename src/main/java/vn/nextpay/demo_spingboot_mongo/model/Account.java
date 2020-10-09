package vn.nextpay.demo_spingboot_mongo.model;


import com.sun.istack.internal.NotNull;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("Account")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseData {

    @Id
//    @Indexed(name = "_id")
    @Field(value = "_id")
    private ObjectId id;

    private String title;
    private String balance;
    private String expiredAt;

    @NotNull
    private String currency;

    @NotNull
    private String minBalance;

    private String maxBalance;
    private Boolean isFrozen;
    private Boolean isArchived;
    private Boolean onlyVisibleToCreatorApp;
//    private String locationToCreate;
//    private String appToCreate;
//    private String objectClass;
//    private String objectName;
//    private String appSubClass;
//    private String appKey;
//    private String appSubKey;








}
