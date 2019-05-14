import plotly
import plotly.plotly as py
import plotly.graph_objs as go
import csv

iter = '';
prob = '';
gauss = '';
gaussSeidel = '';
jacobi = '';
absG = '';
absGS = '';
absJ = '';

iterations = [];
mcResults = [];
gaussResults = [];
gaussSeidelResults = [];
jacobiResults = [];
absGauss = [];
absGaussSeidel = [];
absJacobi = [];

fileName = 'MonteCarlo for ITER_100000N_5 YES_1 NO_2.csv';

plotly.tools.set_credentials_file(username='fruukus', api_key='glKc4Ak2w1x4r5fQl3Q0')
with open(fileName) as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    line_count = 0
    for row in csv_reader:
        if line_count == 0:
            iter = row[0];
            prob = row[1];
            gauss = row[2];
            gaussSeidel = row[3];
            jacobi = row[4];
            absG = row[5];
            absGS = row[6];
            absJ = row[7];
            line_count += 1
        else:
            iterations.append(row[0]);
            mcResults.append(row[1]);
            gaussResults.append(row[2]);
            gaussSeidelResults.append(row[3]);
            jacobiResults.append(row[4]);
            absGauss.append(row[5]);
            absGaussSeidel.append(row[6]);
            absJacobi.append(row[7]);
            line_count += 1


# Create traces
trace0 = go.Scatter(
    x = iterations,
    y = jacobiResults,
    mode = 'lines',
    name = 'wynik Jacobi'
)

trace1 = go.Scatter(
    x = iterations,
    y = gaussResults,
    mode = 'lines',
    name = 'wynik Gauss'
)

trace2 = go.Scatter(
    x = iterations,
    y = gaussSeidelResults,
    mode = 'lines',
    name = 'wynik Gauss-Seidel'
)

trace3 = go.Scatter(
    x = iterations,
    y = mcResults,
    mode = 'lines',
    name = 'wynik Monte Carlo'
)

# trace2 = go.Scatter(
#     x = iterations,
#     y = absJacobi,
#     mode = 'lines',
#     name = 'ABS Jacobi'
# )

data = [trace0, trace1, trace2, trace3]

layout = dict(title = 'Dla N=5, YES=1, NO=2',
              xaxis = dict(title = 'Ilość iteracji'),
              yaxis = dict(title = 'Wyniki'),
              )
fig = dict(data=data, layout=layout)
py.plot(fig, filename='MonteCarlo_n5')