#Linux based image containing java and android sdk for building gradle projects
docker-dev-virtual.artifacts.medtronic.com/mcms-gradle-android-sdk:latest
MAINTAINER david.c.stenzel@medtronic.com

# Copy project to container
COPY . /src
RUN chmod u+x /src/testScript.sh
RUN chmod u+x /src/deployScript.sh

WORKDIR /src

# Entrypoint is to run shell script
ENTRYPOINT ["sh"]
# Default is the test script
CMD ["testScript.sh"]


CMD /bin/bash
