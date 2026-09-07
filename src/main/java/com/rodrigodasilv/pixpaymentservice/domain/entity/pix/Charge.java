package com.rodrigodasilv.pixpaymentservice.domain.entity.pix;

import com.rodrigodasilv.pixpaymentservice.domain.entity.pix.key.Key;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Charge {
    private final Key key;
    private final Money amount;
    private final Merchant merchant;
    private final String description;
}
