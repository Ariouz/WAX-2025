echo "[Recommended] Run this command in a graalpy venv"
sleep 3

export BUILD_TEST=0
export CXXFLAGS="-std=c++17"
export _GLIBCXX_USE_CXX11_ABI=0
export CMAKE_POLICY_VERSION_MINIMUM=3.5
export CC=gcc-12
export CXX=g++-12

graalpy -m pip install torch==2.2.1

