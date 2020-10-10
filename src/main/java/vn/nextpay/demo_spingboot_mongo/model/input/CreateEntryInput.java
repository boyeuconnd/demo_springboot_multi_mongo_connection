package vn.nextpay.demo_spingboot_mongo.model.input;

import lombok.Data;

@Data
public class CreateEntryInput {

    private String owner;
    private String expectBalance;
}
