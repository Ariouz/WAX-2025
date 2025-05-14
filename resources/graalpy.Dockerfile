ARG FINAL_BASE_STAGE=cpu-stage
                                               ##### BASE STAGE #####
FROM oraclelinux:8 AS base-stage

ENV GRAALPY_VERSION=24.1.1 \
    GRAALPY_SHORT_VERSION=24.1 \
    GRAALPY_HOME=/usr/local/graalpy-${GRAALPY_VERSION}

# pytorch build options
ENV _GLIBCXX_USE_CXX11_ABI=0 \
    CXXFLAGS="-std=c++17" \
    CMAKE_POLICY_VERSION_MINIMUM=3.5 \
    PATH=/usr/local/protobuf/bin:${GRAALPY_HOME}/bin:${PATH} \
    CPLUS_INCLUDE_PATH=/usr/include/c++/8:$CPLUS_INCLUDE_PATH \

COPY build /sources/build
WORKDIR /tmp
RUN \
    trust anchor /sources/build/ca-olea.crt && \
    dnf update -y && \
    dnf install -y --setopt=install_weak_deps=False curl git wget && \
    \
    # GraalPy
    sh /sources/build/scripts/install-graalpy.sh && \
    dnf install --setopt=install_weak_deps=False -y libjpeg-turbo-devel zlib-devel && \
    \
    # Build tools \
    dnf install -y --setopt=install_weak_deps=False gcc-toolset-10 \
     glibc-devel \
     glibc-headers \
     libstdc++-devel && \
    source /opt/rh/gcc-toolset-10/enable && \
    \
    dnf groupinstall "Development Tools" && \
    yum install https://dl.fedoraproject.org/pub/epel/epel-release-latest-8.noarch.rpm && \
    \
    dnf clean all && yum clean all

                                            ##### GPU STAGE #####
FROM base-stage AS gpu-stage

ARG CUSPARSELT_ARCHIVE=libcusparse_lt-linux-x86_64-0.5.0.1-archive
ENV CUDA_VERSION=11.8 \
    CUDA_HOME=/usr/local/cuda-${CUDA_VERSION} \
    CUDNN_HOME=/usr \
    CPATH=$CUDA_HOME/include:$CUDNN_HOME/include:$CPATH \
    LIBRARY_PATH=/usr/include:$CUDA_HOME/lib64:$CUDNN_HOME/lib64:$LIBRARY_PATH \
    LD_LIBRARY_PATH=/usr/include:$CUDA_HOME/lib64:$CUDNN_HOME/lib64:$LD_LIBRARY_PATH \
    CPLUS_INCLUDE_PATH=/usr/include/c++/8:$CPLUS_INCLUDE_PATH \
    PATH=$CUDA_HOME/bin:$PATH \
    USE_CUDA=1 \
    USE_CUDNN=1 \
    USE_CUSPARSELT=1

RUN  \
    # Cuda toolkit, cudnn, cuSPARSELt \
    dnf config-manager --add-repo https://developer.download.nvidia.com/compute/cuda/repos/rhel8/x86_64/cuda-rhel8.repo && \
    dnf -y --setopt=install_weak_deps=False install  \
      cuda-toolkit-11-8 && \
    dnf -y module install nvidia-driver:latest-dkms && \
    wget https://developer.download.nvidia.com/compute/cusparselt/redist/libcusparse_lt/linux-x86_64/${CUSPARSELT_ARCHIVE}.tar.xz && \
    tar -xf ${CUSPARSELT_ARCHIVE}.tar.xz && \
    cp ${CUSPARSELT_ARCHIVE}/include/* /usr/include && \
    cp ${CUSPARSELT_ARCHIVE}/lib/* /usr/lib && \
    rm -rf ${CUSPARSELT_ARCHIVE} ${CUSPARSELT_ARCHIVE}.tar.xz && \
    wget https://developer.download.nvidia.com/compute/redist/cudnn/v8.7.0/local_installers/11.8/cudnn-local-repo-rhel9-8.7.0.84-1.0-1.x86_64.rpm -O cudnn-8.7.0-repo.rpm && \
    rpm -i cudnn-8.7.0-repo.rpm && \
    ls /var/cudnn-local-repo-rhel9-8.7.0.84 && \
    cp /var/cudnn-local-repo-rhel9-8.7.0.84/*.gpg /etc/pki/rpm-gpg/ && \
    dnf install -y libcudnn8 libcudnn8-devel && \
    rm -rf cudnn-8.7.0-repo.rpm && \
    dnf clean all && yum clean all

                                            ##### CPU STAGE #####
FROM base-stage AS cpu-stage
# disable gpu support in pytorch build
ENV USE_CUDA=0 \
    USE_CUDNN=0 \
    USE_CUSPARSELT=0 \
    BUILD_TEST=0

                                            ##### FINAL STAGE #####
# FINAL_BASE_STAGE=gpu-stage or cpu-stage
FROM ${FINAL_BASE_STAGE} AS final-stage

RUN \
    # Protobuf
    wget https://github.com/protocolbuffers/protobuf/releases/download/v31.0-rc1/protoc-31.0-rc-1-linux-x86_64.zip && \
    unzip protoc-31.0-rc-1-linux-x86_64.zip -d protobuf && \
    cp -r protobuf /usr/local/ && \
    rm -rf protobuf && \
    \
    # GraalPy requirements
    yum install ccache && \
    yum install libffi libffi-devel && \
    \
    # GraalPy packages
    dnf install --setopt=install_weak_deps=False -y patch which && \
    graalpy -m ensurepip && \
    graalpy -m pip install \
    torchvision==0.17.1 \
    numpy==1.24.3 \
    pillow==11.2.1 \
#    simpleitk==2.5.0 \ # incompatible ?
    scikit-build \
    monai && \
    dnf clean all && yum clean all


ENTRYPOINT ["echo", "Hello World"]