package com.rodrigodasilv.pixpaymentservice.domain.entity.pix;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@RequiredArgsConstructor
@Getter
public class Charge {
    private final Key key;
    private final Money amount;
    private final Merchant merchant;
    private final String description;
}
