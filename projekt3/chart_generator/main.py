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

fileName = 'MonteCarlo for ITER_100000N_30 YES_15 NO_12.csv';

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
    y = absGauss,
    mode = 'lines',
    name = 'ABS Gauss'
)

trace1 = go.Scatter(
    x = iterations,
    y = absGaussSeidel,
    mode = 'lines',
    name = 'ABS Gauss-Seidel'
)

trace2 = go.Scatter(
    x = iterations,
    y = absJacobi,
    mode = 'lines',
    name = 'ABS Jacobi'
)

data = [trace0, trace1, trace2]

layout = dict(title = 'Porównanie błędów bezwzględnych poszczególnych metod z MonteCarlo',
              xaxis = dict(title = 'Ilość iteracji'),
              yaxis = dict(title = 'Błąd bezwzględny'),
              )
fig = dict(data=data, layout=layout)
py.plot(fig, filename='line-mode')