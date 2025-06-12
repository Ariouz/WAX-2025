package org.example.javase;

import org.graalvm.polyglot.io.ByteSequence;

interface BytesIO {
    ByteSequence getvalue();
}