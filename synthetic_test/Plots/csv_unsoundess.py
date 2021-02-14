import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.style as style

def add_f1String(meanDataFrame, dataFrame, cg_name):
    visited_categories = []
    total_greater_than_zero = 0
    for i, row in meanDataFrame.iterrows():
        if row['Category'] not in visited_categories:
            category_rows = dataFrame.loc[dataFrame['Category'] == row['Category']]
            numerator = len(category_rows.loc[category_rows[cg_name + '_F1Score'] > 0])
            total_greater_than_zero += numerator
            denominator = len(category_rows)
            meanDataFrame.at[i, cg_name + '_F1String'] = str(numerator) + '/' + str(denominator)
            visited_categories.append(row['Category'])
    return total_greater_than_zero

def get_geometric_mean(dataset, metric):
    """
    Habibs geometric mean
    """
    import numpy as np
    import math

    zeroes = []
    non_zeroes = []
    sum_of_logs = 0.0
    for index, row in dataset.iterrows():
        if row[metric] > 0:
            non_zeroes.append(row[metric])
            sum_of_logs += np.log2(row[metric])
        else:
            zeroes.append(row[metric])
    m = len(zeroes)
    n = len(non_zeroes)
    nbynplusm = n/(n + m)
    right_side_of_exp = (1/(n + m)) * sum_of_logs
    exp_value = math.exp(right_side_of_exp)
    geometric_mean = nbynplusm * exp_value
    return geometric_mean

c2f = pd.read_csv('../docker_reports/Code2flow.csv')
pyan = pd.read_csv('../docker_reports/Pyan.csv')
walaNCFA = pd.read_csv('../docker_reports/WalaNCFA.csv')

c2f = c2f[['Category', 'File', 'Precision', 'Recall', 'F1Score']]
c2f = c2f.rename(columns={'F1Score': 'c2f_F1Score'})

pyan = pyan[['Category', 'File', 'Precision', 'Recall', 'F1Score']]
pyan = pyan.rename(columns={'F1Score': 'pyan_F1Score'})

walaNCFA = walaNCFA[['Category', 'File', 'Precision', 'Recall', 'F1Score']]
walaNCFA = walaNCFA.rename(columns={'F1Score': 'ncfa_F1Score'})

c2f_mean = c2f.groupby(['Category'], as_index=False).mean()
pyan_mean = pyan.groupby(['Category'], as_index=False).mean()
walaNCFA_mean = walaNCFA.groupby(['Category'], as_index=False).mean()

c2f_avg_num = add_f1String(c2f_mean, c2f, 'c2f')
pyan_avg_num = add_f1String(pyan_mean, pyan, 'pyan')
ncfa_avg_num = add_f1String(walaNCFA_mean, walaNCFA, 'ncfa')

c2f_f1 = c2f_mean[['Category', 'c2f_F1Score', 'c2f_F1String']]
c2f_f1 = c2f_f1.append({'Category': 'Average', 'c2f_F1Score': get_geometric_mean(c2f_f1, "c2f_F1Score"), 'c2f_F1String': str(c2f_avg_num) + '/' + str(len(c2f['File']))}, ignore_index=True)
pyan_f1 = pyan_mean[['Category', 'pyan_F1Score', 'pyan_F1String']]
pyan_f1 = pyan_f1.append({'Category': 'Average', 'pyan_F1Score': get_geometric_mean(pyan_f1, "pyan_F1Score"), 'pyan_F1String': str(pyan_avg_num) + '/' + str(len(pyan['File']))}, ignore_index=True)
ncfa_f1 = walaNCFA_mean[['Category', 'ncfa_F1Score', 'ncfa_F1String']]
ncfa_f1 = ncfa_f1.append({'Category': 'Average', 'ncfa_F1Score': get_geometric_mean(ncfa_f1, "ncfa_F1Score"), 'ncfa_F1String': str(ncfa_avg_num) + '/' + str(len(walaNCFA['File']))}, ignore_index=True)

temp = pd.merge(c2f_f1, pyan_f1, on='Category')
f1 = pd.merge(temp, ncfa_f1, on='Category')

f1.to_csv("Unsoundness.csv")