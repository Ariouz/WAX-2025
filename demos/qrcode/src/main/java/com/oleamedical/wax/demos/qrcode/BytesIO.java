package com.oleamedical.wax.demos.qrcode;

import org.graalvm.polyglot.io.ByteSequence;

interface BytesIO {
    ByteSequence getvalue();
}