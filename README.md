# flume-fuzzy-interceptor

Interceptor is used for perform fuzzySQL query on FlumeEvents objects.

#### How to
If you want to create interceptor run _maven package_ stage. Copy .jar from target directory and paste it to flume-bin/lib directory.
In the next step change settings and run application. 

> agent.sources.httpSource.interceptors = i

> agent.sources.httpSource.interceptors.i.type = pl.polsl.bdis.interceptors.FuzzySqlInterceptor$Builder

__remember!__ key and value always in one line.