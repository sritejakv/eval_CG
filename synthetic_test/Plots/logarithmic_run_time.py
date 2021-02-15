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
        if (index not in [14, 29, 44]):
            height = rect.get_height()
            if rect.get_width() > 0:
                plot_axes.text(rect.get_width(), rect.get_y() + height/2, "%.3f" % rect.get_width(), fontsize=10, color='black', alpha=0.8, ha='center', va='bottom')
        index += 1
    plot_axes.text(rects[14].get_width(), rects[14].get_y() + height/2, "%.3f" % rects[14].get_width(), fontsize=10, ha='center', va='bottom', weight='bold', style='italic')
    plot_axes.text(rects[29].get_width(), rects[29].get_y() + height/2, "%.3f" % rects[29].get_width(), fontsize=10, ha='center', va='bottom', weight='bold', style='italic')
    plot_axes.text(rects[-1].get_width(), rects[-1].get_y() + height/2, "%.3f" % rects[-1].get_width(), fontsize=10, ha='center', va='bottom', weight='bold', style='italic')


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

        total_files = len(run1['File'])

        run1 = run1[['Category', 'Runtime']]
        run1.replace({"code_generation": "run_time_code_generation"}, inplace=True)
        run1.loc[len(run1)] = ['Average', run1['Runtime'].sum()/total_files]
        run1 = run1.groupby(['Category'], as_index=False).mean()
        # run1 = run1.loc[(run1!=0.0).all(axis=1)]

        run2 = run2[['Category', 'Runtime']]
        run2.replace({"code_generation": "run_time_code_generation"}, inplace=True)
        run2.loc[len(run2)] = ['Average', run2['Runtime'].sum()/total_files]
        run2 = run2.groupby(['Category'], as_index=False).mean()
        # run2 = run2.loc[(run2!=0.0).all(axis=1)]
        
        run3 = run3[['Category', 'Runtime']]
        run3.replace({"code_generation": "run_time_code_generation"}, inplace=True)
        run3.loc[len(run3)] = ['Average', run3['Runtime'].sum()/total_files]
        run3 = run3.groupby(['Category'], as_index=False).mean()
        # run3 = run3.loc[(run3!=0.0).all(axis=1)]
        
        run4 = run4[['Category', 'Runtime']]
        run4.replace({"code_generation": "run_time_code_generation"}, inplace=True)
        run4.loc[len(run4)] = ['Average', run4['Runtime'].sum()/total_files]
        run4 = run4.groupby(['Category'], as_index=False).mean()
        # run4 = run4.loc[(run4!=0.0).all(axis=1)]

        run5 = run5[['Category', 'Runtime']]
        run5.replace({"code_generation": "run_time_code_generation"}, inplace=True)
        run5.loc[len(run5)] = ['Average', run5['Runtime'].sum()/total_files]
        run5 = run5.groupby(['Category'], as_index=False).mean()
        # run5 = run5.loc[(run5!=0.0).all(axis=1)]
        
        run6 = run6[['Category', 'Runtime']]
        run6.replace({"code_generation": "run_time_code_generation"}, inplace=True)
        run6.loc[len(run6)] = ['Average', run6['Runtime'].sum()/total_files]
        run6 = run6.groupby(['Category'], as_index=False).mean()
        # run6 = run6.loc[(run6!=0.0).all(axis=1)]

        run7 = run7[['Category', 'Runtime']]
        run7.replace({"code_generation": "run_time_code_generation"}, inplace=True)
        run7.loc[len(run7)] = ['Average', run7['Runtime'].sum()/total_files]
        run7 = run7.groupby(['Category'], as_index=False).mean()
        # run7 = run7.loc[(run7!=0.0).all(axis=1)]

        run8 = run8[['Category', 'Runtime']]
        run8.replace({"code_generation": "run_time_code_generation"}, inplace=True)
        run8.loc[len(run8)] = ['Average', run8['Runtime'].sum()/total_files]
        run8 = run8.groupby(['Category'], as_index=False).mean()
        # run8 = run8.loc[(run8!=0.0).all(axis=1)]

        run9 = run9[['Category', 'Runtime']]
        run9.replace({"code_generation": "run_time_code_generation"}, inplace=True)
        run9.loc[len(run9)] = ['Average', run9['Runtime'].sum()/total_files]
        run9 = run9.groupby(['Category'], as_index=False).mean()
        # run9 = run9.loc[(run9!=0.0).all(axis=1)]

        run10 = run10[['Category', 'Runtime']]
        run10.replace({"code_generation": "run_time_code_generation"}, inplace=True)
        run10.loc[len(run10)] = ['Average', run10['Runtime'].sum()/total_files]
        run10 = run10.groupby(['Category'], as_index=False).mean()
        # run10 = run10.loc[(run10!=0.0).all(axis=1)]

        temp = pd.concat([run1,run2, run3, run4, run5, run6, run7, run8, run9, run10], axis=0)
        standard_dev = temp.groupby(['Category']).std()
        return standard_dev
    except Exception as e:
        print("Exception in calculating std: " + str(e))
        return None

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

colors = ['#DA7C30', '#396AB1', '#CC2529']

c2f_main = pd.read_csv('../docker_reports/Code2flow.csv')
c2f = c2f_main[['Category', 'Runtime']]
pyan_main = pd.read_csv('../docker_reports/Pyan.csv')
pyan = pyan_main[['Category', 'Runtime']]
walaNCFA_main = pd.read_csv('../docker_reports/WalaNCFA.csv')
walaNCFA = walaNCFA_main[['Category', 'Runtime']].copy(deep=True)

c2f_mean = c2f.groupby(['Category'], as_index=False).mean()
c2f_mean.loc[len(c2f_mean)] = ['Average', get_geometric_mean(c2f, "Runtime")]
pyan_mean = pyan.groupby(['Category'], as_index=False).mean()
pyan_mean.loc[len(pyan_mean)] = ['Average', get_geometric_mean(pyan, "Runtime")]
walaNCFA['Runtime'] = walaNCFA['Runtime'] * 0.001
walaNCFA_mean = walaNCFA.groupby(['Category'], as_index=False).mean()
walaNCFA_mean.loc[len(walaNCFA_mean)] = ['Average', get_geometric_mean(walaNCFA, "Runtime")]

c2f_runtime = c2f_mean[['Category', 'Runtime']].copy()
c2f_runtime.replace({"code_generation": "run_time_code_generation"}, inplace=True)
c2f_runtime.rename(columns={'Runtime': 'Code2flow'}, inplace=True)
pyan_runtime = pyan_mean[['Category', 'Runtime']].copy()
pyan_runtime.replace({"code_generation": "run_time_code_generation"}, inplace=True)
pyan_runtime.rename(columns={'Runtime': 'Pyan'}, inplace=True)
walaNCFA_runtime = walaNCFA_mean[['Category', 'Runtime']].copy()
walaNCFA_runtime.replace({"code_generation": "run_time_code_generation"}, inplace=True)
walaNCFA_runtime.rename(columns={'Runtime': 'Wala NCFA'}, inplace=True)

temp_df = pd.merge(c2f_runtime, pyan_runtime, on='Category')
merged_runtime = pd.merge(temp_df, walaNCFA_runtime, on='Category')
plot_df = pd.DataFrame({'Code2flow': merged_runtime['Code2flow'].values.tolist(), 'Pyan': merged_runtime['Pyan'].values.tolist(), 
                'Wala NCFA': merged_runtime['Wala NCFA'].values.tolist()}, index=merged_runtime['Category'].values.tolist())

ind = np.arange(len(plot_df))
width = 0.3
label_fontsize = 10
error_bar_kwargs = dict(lw=1, capsize=2, capthick=1)
fig, ax = plt.subplots(figsize=(9, 15))

# c2f_err_bars = get_error_bars('Code2flow')
# c2f_err = []
# for i in c2f_runtime['Category']:
#     c2f_err.append(c2f_err_bars.loc[i]['Runtime'])

# pyan_err_bars = get_error_bars('Pyan')
# pyan_err = []
# for i in pyan_runtime['Category']:
#     pyan_err.append(pyan_err_bars.loc[i]['Runtime'])

# ncfa_err_bars = get_error_bars('WalaNCFA')
# ncfa_err = []
# for i in walaNCFA_runtime['Category']:
#     ncfa_err.append(ncfa_err_bars.loc[i]['Runtime'])

ax.barh(ind, plot_df['Code2flow'], width, color=colors[0], alpha=0.6, label='Code2flow', error_kw=error_bar_kwargs)
ax.barh(ind + width, plot_df['Pyan'], width, color=colors[1], alpha=0.6, label='Pyan', error_kw=error_bar_kwargs)
ax.barh(ind + width + width, plot_df['Wala NCFA'], width, color=colors[2], alpha=0.6, label='Wala NCFA', error_kw=error_bar_kwargs)
plt.xscale('log')

ax.set(yticks=ind + width, yticklabels=merged_runtime['Category'].values.tolist(), ylim=[2*width - 1, len(plot_df)])
ax.set_xlabel('Runtime (ms)')
ax.set_ylabel('Benchmark Category')
ax.legend(loc='upper right', bbox_to_anchor=(0.5, 0.6, 0.5, 0.5))

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

fig.savefig('log_runtime_synthetic_test.png', transparent=False, dpi=150, bbox_inches="tight")