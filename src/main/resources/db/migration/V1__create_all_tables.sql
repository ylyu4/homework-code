CREATE TABLE `commodity`
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku            varchar(64)  NOT NULL,
    title          varchar(64)  NOT NULL,
    description    varchar(256) NULL
);

CREATE TABLE `customer`
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    username       varchar(32)  NOT NULL,
    password       varchar(128)  NOT NULL
);

CREATE TABLE `price`
(
    id       BIGINT AUTO_INCREMENT  PRIMARY KEY,
    amount         INT          NOT NULL
);

CREATE TABLE `shipping_address`
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    address        varchar(128) NOT NULL,
    full_name      varchar(64)  NOT NULL,
    phone_number   varchar(32)  NOT NULL
);

CREATE TABLE `image`
(
    id       BIGINT AUTO_INCREMENT  PRIMARY KEY,
    url            varchar(128)  NOT NULL,
    alternate_text  varchar(256) NULL
);

CREATE TABLE `orders`
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity       INT    NOT NULL,
    total_price    INT    NOT NULL,
    order_status   varchar(32) NOT NULL
);
