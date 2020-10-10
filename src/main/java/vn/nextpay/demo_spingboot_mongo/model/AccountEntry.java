package vn.nextpay.demo_spingboot_mongo.model;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "AccountEntries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntry extends BaseData {

    @Id
    @Field(value = "_id")
    private ObjectId id;

    private ObjectId accountId;

    @NotNull
    private Long entryCounter;

    private String beforeBalance;

    private String amount;

    private String afterBalance;

    private String note;

    private String source;

    private String sourceId;

    private String sourceKey;



}
