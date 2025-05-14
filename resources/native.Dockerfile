FROM container-registry.oracle.com/graalvm/native-image:23

COPY build/settings.xml .
RUN --mount=type=cache,target=/home/.m2 microdnf install -y \
    maven \
    git \
    openssh \
    rsync \
    && mkdir -p /home/.m2 \
    && cp settings.xml /usr/share/maven/conf/