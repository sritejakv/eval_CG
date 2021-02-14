import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.style as style

def autolabel(rects, plot_axes):
    """
    Attach a text label above each bar displaying its width
    """
    totals = []
    for i in rects:
        totals.append(i.get_width())
    total = sum(totals)
    for rect in rects[:-1]:
        height = rect.get_height()
        if rect.get_width() > 0:
            plot_axes.text(rect.get_width(), rect.get_y() + height/2, "%.2f" % rect.get_width(), fontsize=7, color='black', alpha=0.8, ha='center', va='bottom')
    plot_axes.text(rects[-1].get_width(), rects[-1].get_y() + height/2, "%.2f" % rects[-1].get_width(), fontsize=7, ha='center', va='bottom', weight='bold', style='italic')

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


style.use(['ggplot', 'fivethirtyeight'])

colors = ['#DA7C30', '#396AB1', '#CC2529', '#47CDDA']

c2f_main = pd.read_csv('../docker_reports/Code2flow.csv')
c2f = c2f_main[['Category', 'Precision']]
pyan_main = pd.read_csv('../docker_reports/Pyan.csv')
pyan = pyan_main[['Category', 'Precision']]
walaNCFA_main = pd.read_csv('../docker_reports/WalaNCFA.csv')
walaNCFA = walaNCFA_main[['Category', 'Precision']]

c2f_mean = c2f.groupby(['Category'], as_index=False).mean()
# c2f_mean.loc[len(c2f_mean)] = ['Weighted Average', get_weighted_geometric_mean(c2f_main)]
c2f_mean.loc[len(c2f_mean)] = ['Average', get_geometric_mean(c2f_main, "Precision")]
pyan_mean = pyan.groupby(['Category'], as_index=False).mean()
# pyan_mean.loc[len(pyan_mean)] = ['Weighted Average', get_weighted_geometric_mean(pyan_main)]
pyan_mean.loc[len(pyan_mean)] = ['Average', get_geometric_mean(pyan_main, "Precision")]
walaNCFA_mean = walaNCFA.groupby(['Category'], as_index=False).mean()
# walaNCFA_mean.loc[len(walaNCFA_mean)] = ['Weighted Average', get_weighted_geometric_mean(walaNCFA_main)]
walaNCFA_mean.loc[len(walaNCFA_mean)] = ['Average', get_geometric_mean(walaNCFA_main, "Precision")]


c2f_precision = c2f_mean[['Category', 'Precision']].copy()
c2f_precision.replace({"code_generation": "run_time_code_generation"}, inplace=True)
pyan_precision = pyan_mean[['Category', 'Precision']].copy()
pyan_precision.replace({"code_generation": "run_time_code_generation"}, inplace=True)
walaNCFA_precision = walaNCFA_mean[['Category', 'Precision']].copy()
walaNCFA_precision.replace({"code_generation": "run_time_code_generation"}, inplace=True)

label_fontsize = 10
title_fontsize = 11

fig, (ax0, ax1, ax2) = plt.subplots(nrows=1, ncols=3, sharey=True, figsize=(9, 4))
c2f_precision.plot(kind='barh', y='Precision', x='Category', color=colors[0], alpha=0.6, ax=ax0)
ax0.set_title('Code2flow', fontsize=title_fontsize)
ax0.set_xlabel('Precision', fontsize=label_fontsize)
ax0.set_ylabel('Benchmark Category', fontsize=label_fontsize)
ax0.set_xlim([0, 1])

pyan_precision.plot(kind='barh', y='Precision', x='Category', color=colors[1], alpha=0.6, ax=ax1)
ax1.set_title('Pyan', fontsize=title_fontsize)
ax1.set_xlabel('Precision', fontsize=label_fontsize)
ax1.set_xlim([0, 1])

walaNCFA_precision.plot(kind='barh', y='Precision', x='Category', color=colors[2], alpha=0.6, ax=ax2)
ax2.set_title('Wala NCFA', fontsize=title_fontsize)
ax2.set_xlabel('Precision', fontsize=label_fontsize)
ax2.set_xlim([0, 1])

ax0.legend().set_visible(False)
ax1.legend().set_visible(False)
ax2.legend().set_visible(False)

tick_label_size = 8
ax0.tick_params(labelsize=tick_label_size)
ax1.tick_params(labelsize=tick_label_size)
ax2.tick_params(labelsize=tick_label_size)

#Setting weight for Average row
ylabels = ax0.get_yticklabels()
modified_ylabels = []
for i in ylabels:
    if 'Average' in i.get_text():
        i.set_weight("bold")
        i.set_style("italic")
    
    modified_ylabels.append(i)
ax0.set_yticklabels(modified_ylabels)

#Adding values next to the bars
autolabel(ax0.patches, ax0)
autolabel(ax1.patches, ax1)
autolabel(ax2.patches, ax2)
# autolabel(ax3.patches, ax3)

fig.savefig('precision_construct_test.png', transparent=False, dpi=150, bbox_inches="tight")