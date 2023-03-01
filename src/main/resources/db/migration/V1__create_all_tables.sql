CREATE TABLE `customer`
(
    customer_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    username       varchar(32)  NOT NULL,
    password       varchar(128)  NOT NULL
);

CREATE TABLE `price`
(
    price_id       BIGINT AUTO_INCREMENT  PRIMARY KEY,
    amount         INT          NOT NULL
);

CREATE TABLE `image`
(
    url            varchar(128) PRIMARY KEY,
    alternate_text  varchar(256) NULL
);

CREATE TABLE `shipping_address`
(
    address_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    address        varchar(128) NOT NULL,
    full_name      varchar(64)  NOT NULL,
    phone_number   varchar(32)  NOT NULL
);

CREATE TABLE `commodity`
(
    sku            varchar(64)  PRIMARY KEY,
    title          varchar(64)  NOT NULL,
    description    varchar(256) NULL,
    price_id       BIGINT       NOT NULL,
    FOREIGN KEY (price_id) REFERENCES price(price_id)
);


CREATE TABLE `order`
(
    order_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity       INT    NOT NULL,
    total_price    INT    NOT NULL,
    order_status   varchar(32) NOT NULL,
    customer_id    BIGINT NOT NULL,
    sku            varchar(64) NOT NULL,
    address_id     BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    FOREIGN KEY (sku) REFERENCES commodity(sku),
    FOREIGN KEY (address_id) REFERENCES shipping_address(address_id)
);
