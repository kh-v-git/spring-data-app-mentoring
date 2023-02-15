#Get latest DB from repository
FROM mcr.microsoft.com/mssql/server:2019-CU16-ubuntu-20.04

RUN echo "Start preparing DB image..."
#SetUp environment variables
ENV ACCEPT_EULA=Y

RUN echo "Start coping scripts to container folder..."
COPY data /

RUN echo "Executing db build scripts..."
#Run init script
ENTRYPOINT [ "/bin/bash", "entrypoint.sh" ]
CMD [ "/opt/mssql/bin/sqlservr" ]