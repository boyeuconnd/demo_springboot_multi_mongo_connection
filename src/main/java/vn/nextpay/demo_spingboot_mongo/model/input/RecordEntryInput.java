package vn.nextpay.demo_spingboot_mongo.model.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordEntryInput {

    private ObjectId accountId;
    private String owner;
    private String amount;
}
