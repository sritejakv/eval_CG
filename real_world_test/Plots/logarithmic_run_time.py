import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.style as style
from matplotlib.ticker import FormatStrFormatter

def autolabel(rects, plot_axes):
    """
    Attach a text label above each bar displaying its width
    """
    totals = []
    for i in rects:
        totals.append(i.get_width())
    total = sum(totals)
    
    index = 0
    for rect in rects:
        if (index not in [5, 11, 17]):
            height = rect.get_height()
            if rect.get_width() > 0:
                plot_axes.text(rect.get_width(), rect.get_y() + height/2, "%.3f" % rect.get_width(), fontsize=6.5, color='black', alpha=0.8, ha='center', va='bottom')
        index += 1
    plot_axes.text(rects[5].get_width(), rects[5].get_y() + height/2, "%.3f" % rects[5].get_width(), fontsize=6.5, ha='center', va='bottom', weight='bold', style='italic')
    plot_axes.text(rects[11].get_width(), rects[11].get_y() + height/2, "%.3f" % rects[11].get_width(), fontsize=6.5, ha='center', va='bottom', weight='bold', style='italic')
    plot_axes.text(rects[-1].get_width(), rects[-1].get_y() + height/2, "%.3f" % rects[-1].get_width(), fontsize=6.5, ha='center', va='bottom', weight='bold', style='italic')


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

def get_error_bars(framework):
    try:
        run1 = pd.read_csv('../docker_reports/run1/'+ framework +'.csv')
        run2 = pd.read_csv('../docker_reports/run2/'+ framework +'.csv')
        run3 = pd.read_csv('../docker_reports/run3/'+ framework +'.csv')
        run4 = pd.read_csv('../docker_reports/run4/'+ framework +'.csv')
        run5 = pd.read_csv('../docker_reports/run5/'+ framework +'.csv')
        run6 = pd.read_csv('../docker_reports/run6/'+ framework +'.csv')
        run7 = pd.read_csv('../docker_reports/run7/'+ framework +'.csv')
        run8 = pd.read_csv('../docker_reports/run8/'+ framework +'.csv')
        run9 = pd.read_csv('../docker_reports/run9/'+ framework +'.csv')
        run10 = pd.read_csv('../docker_reports/run10/'+ framework +'.csv')

        run1 = run1[['RealWorldLib', 'Runtime']]
        run1.loc[len(run1)] = ['Average', run1['Runtime'].mean()]
        run2 = run2[['RealWorldLib', 'Runtime']]
        run2.loc[len(run2)] = ['Average', run2['Runtime'].mean()]
        run3 = run3[['RealWorldLib', 'Runtime']]
        run3.loc[len(run3)] = ['Average', run3['Runtime'].mean()]
        run4 = run4[['RealWorldLib', 'Runtime']]
        run4.loc[len(run4)] = ['Average', run4['Runtime'].mean()]
        run5 = run5[['RealWorldLib', 'Runtime']]
        run5.loc[len(run5)] = ['Average', run5['Runtime'].mean()]
        run6 = run6[['RealWorldLib', 'Runtime']]
        run6.loc[len(run6)] = ['Average', run6['Runtime'].mean()]
        run7 = run7[['RealWorldLib', 'Runtime']]
        run7.loc[len(run7)] = ['Average', run7['Runtime'].mean()]
        run8 = run8[['RealWorldLib', 'Runtime']]
        run8.loc[len(run8)] = ['Average', run8['Runtime'].mean()]
        run9 = run9[['RealWorldLib', 'Runtime']]
        run9.loc[len(run9)] = ['Average', run9['Runtime'].mean()]
        run10 = run10[['RealWorldLib', 'Runtime']]
        run10.loc[len(run10)] = ['Average', run10['Runtime'].mean()]

        temp = pd.concat([run1, run2, run3, run4, run5, run6, run7, run8, run9, run10])
        standard_dev = temp.groupby(['RealWorldLib']).std()
        return standard_dev
    except Exception as e:
        print("Exception in calculating std: " + str(e))
        return None


style.use(['ggplot', 'fivethirtyeight'])

colors = ['#DA7C30', '#396AB1', '#CC2529']

error_bar_kwargs = dict(lw=1.5, capsize=2.5, capthick=1)

c2f = pd.read_csv('../docker_reports/Code2flow.csv')
c2f = c2f[['RealWorldLib', 'Runtime']]
pyan = pd.read_csv('../docker_reports/Pyan.csv')
pyan = pyan[['RealWorldLib', 'Runtime']]
walaNCFA = pd.read_csv('../docker_reports/WalaNCFA.csv')
walaNCFA = walaNCFA[['RealWorldLib', 'Runtime']].copy(deep=True)

c2f_mean = c2f
c2f_mean.loc[len(c2f_mean)] = ['Average', get_geometric_mean(c2f, "Runtime")]
pyan_mean = pyan
pyan_mean.loc[len(pyan_mean)] = ['Average', get_geometric_mean(pyan, "Runtime")]
walaNCFA['Runtime'] = walaNCFA['Runtime'] * 0.001
walaNCFA_mean = walaNCFA
walaNCFA_mean.loc[len(walaNCFA_mean)] = ['Average', get_geometric_mean(walaNCFA, "Runtime")]

c2f_runtime = c2f_mean[['RealWorldLib', 'Runtime']].copy()
c2f_runtime.rename(columns={'Runtime': 'Code2flow'}, inplace=True)
pyan_runtime = pyan_mean[['RealWorldLib', 'Runtime']].copy()
pyan_runtime.rename(columns={'Runtime': 'Pyan'}, inplace=True)
walaNCFA_runtime = walaNCFA_mean[['RealWorldLib', 'Runtime']].copy()
walaNCFA_runtime.rename(columns={'Runtime': 'Wala NCFA'}, inplace=True)

temp_df = pd.merge(c2f_runtime, pyan_runtime, on='RealWorldLib')
merged_runtime = pd.merge(temp_df, walaNCFA_runtime, on='RealWorldLib')
plot_df = pd.DataFrame({'Code2flow': merged_runtime['Code2flow'].values.tolist(), 'Pyan': merged_runtime['Pyan'].values.tolist(), 
                'Wala NCFA': merged_runtime['Wala NCFA'].values.tolist()}, index=merged_runtime['RealWorldLib'].values.tolist())

ind = np.arange(len(plot_df))
width = 0.3
label_fontsize = 10
error_bar_kwargs = dict(lw=1, capsize=2, capthick=1)
fig, ax = plt.subplots(figsize=(5, 3.75))

# c2f_err_bars = get_error_bars('Code2flow')
# c2f_err = []
# for i in c2f_runtime['RealWorldLib']:
#     c2f_err.append(c2f_err_bars.loc[i]['Runtime'])

# pyan_err_bars = get_error_bars('Pyan')
# pyan_err = []
# for i in pyan_runtime['RealWorldLib']:
#     pyan_err.append(pyan_err_bars.loc[i]['Runtime'])

# ncfa_err_bars = get_error_bars('WalaNCFA')
# ncfa_err = []
# for i in walaNCFA_runtime['RealWorldLib']:
#     ncfa_err.append(ncfa_err_bars.loc[i]['Runtime'])

ax.barh(ind, plot_df['Code2flow'], width, color=colors[0], alpha=0.6, label='Code2flow', error_kw=error_bar_kwargs)
ax.barh(ind + width, plot_df['Pyan'], width, color=colors[1], alpha=0.6, label='Pyan', error_kw=error_bar_kwargs)
ax.barh(ind + width + width, plot_df['Wala NCFA'], width, color=colors[2], alpha=0.6, label='Wala NCFA', error_kw=error_bar_kwargs)
plt.xscale('log')

ax.set(yticks=ind + width, yticklabels=merged_runtime['RealWorldLib'].values.tolist(), ylim=[2*width - 1, len(plot_df)])
ax.set_xlabel('Runtime (ms)', fontsize=label_fontsize)
ax.set_ylabel('Real-world library', fontsize=label_fontsize)
ax.legend(loc='upper right', bbox_to_anchor=(0.5, 0.74, 0.5, 0.5), prop={"size": 8})

tick_label_size = 8
ax.tick_params(labelsize=tick_label_size)

#Setting weight for Average row
ylabels = ax.get_yticklabels()
modified_ylabels = []
for i in ylabels:
    if i.get_text() == 'Average':
        i.set_weight("bold")
        i.set_style("italic")
    
    modified_ylabels.append(i)
ax.set_yticklabels(modified_ylabels)

#Adding values next to the bars
autolabel(ax.patches, ax)

fig.savefig('log_runtime_real_world_test.png', transparent=False, dpi=150, bbox_inches="tight")
