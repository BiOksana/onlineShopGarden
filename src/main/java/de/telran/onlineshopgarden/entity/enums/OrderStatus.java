package de.telran.onlineshopgarden.entity.enums;

public enum OrderStatus {
    CREATED,
    PENDING_PAYMENT,
    PAID,
    IN_TRANSIT,
    DELIVERED,
    CANCELED;

    public OrderStatus next() {
        OrderStatus[] values = OrderStatus.values();
        int nextIndex = (this.ordinal() + 1) % values.length;
        return values[nextIndex];
    }

}
