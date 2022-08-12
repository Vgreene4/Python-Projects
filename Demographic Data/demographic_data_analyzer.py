import pandas as pd


def calculate_demographic_data(print_data=True):
    # Read data from file
    df = pd.read_csv('adult.data.csv')

    # How many of each race are represented in this dataset? This should be a Pandas series with race names as the index labels. 
    sdata= df['race'].value_counts()
    ser= pd.Series(sdata)
    race_count = ser

    # What is the average age of men?
    men= df[df['sex']== 'Male']
    mean_male_age= men['age'].mean()
    average_age_men = round(mean_male_age, 1)

    # What is the percentage of people who have a Bachelor's degree?
    bachelors= df[df['education']== 'Bachelors']
    percent_w_bachelors= (bachelors.shape[0]/df.shape[0])*100
    percentage_bachelors = round(percent_w_bachelors, 1)

    # What percentage of people with advanced education (`Bachelors`, `Masters`, or `Doctorate`) make more than 50K?
    # What percentage of people without advanced education make more than 50K?
    Masters= df[df['education']== 'Masters']
    Doctorate= df[df['education']== 'Doctorate']
    advanced= bachelors.append(Masters)
    advanced= advanced.append(Doctorate)

    nobat = df[df['education'] != 'Bachelors']
    nomat= nobat[nobat['education']!= 'Masters']
    noadv= nomat[nomat['education']!= 'Doctorate']
    # with and without `Bachelors`, `Masters`, or `Doctorate`
    higher_education = round(advanced.shape[0]/df.shape[0], 1)
  
    lower_education = round(noadv.shape[0]/df.shape[0], 1)

    # percentage with salary >50K
    advanced['salary']= advanced['salary'].astype(str)
    over50k= advanced[advanced['salary']== '>50K']
    percent_advanced_over50k= (over50k.shape[0]/advanced.shape[0])*100
    higher_education_rich = round(percent_advanced_over50k, 1)

    noadv_50k=   (noadv[noadv['salary']==">50K"].shape[0]/noadv.shape[0])*100  
    lower_education_rich = round(noadv_50k, 1)

    # What is the minimum number of hours a person works per week (hours-per-week feature)?
    min_work_hours = df['hours-per-week'].min()

    # What percentage of the people who work the minimum number of hours per week have a salary of >50K?
    num_min_workers = df[df['hours-per-week']==1]
  
    highearners= min_workers[min_workers['salary']==">50K"]
    percenthighearnersmin= (highearners.shape[0]/min_workers.shape[0])*100
    rich_percentage = percenthighearnersmin

    # What country has the highest percentage of people that earn >50K?
    rich= df['salary'] == '>50K'
    new= (df[rich]['native-country'].value_counts()/df['native-country'].value_counts())*100
    country= new.sort_values(ascending= False)
    bestcountry= country.index[0]
  
    highest_earning_country = bestcountry

    percent= new['Iran']
    highest_earning_country_percentage = round(percent, 1)

    # Identify the most popular occupation for those who earn >50K in India.

    india= df[df['native-country']=="India"]
    india_over50k= india[india['salary']=='>50K']
    top_IN_occupation = india_over50k['occupation'].value_counts().index[0]

    # DO NOT MODIFY BELOW THIS LINE

    if print_data:
        print("Number of each race:\n", race_count) 
        print("Average age of men:", average_age_men)
        print(f"Percentage with Bachelors degrees: {percentage_bachelors}%")
        print(f"Percentage with higher education that earn >50K: {higher_education_rich}%")
        print(f"Percentage without higher education that earn >50K: {lower_education_rich}%")
        print(f"Min work time: {min_work_hours} hours/week")
        print(f"Percentage of rich among those who work fewest hours: {rich_percentage}%")
        print("Country with highest percentage of rich:", highest_earning_country)
        print(f"Highest percentage of rich people in country: {highest_earning_country_percentage}%")
        print("Top occupations in India:", top_IN_occupation)

    return {
        'race_count': race_count,
        'average_age_men': average_age_men,
        'percentage_bachelors': percentage_bachelors,
        'higher_education_rich': higher_education_rich,
        'lower_education_rich': lower_education_rich,
        'min_work_hours': min_work_hours,
        'rich_percentage': rich_percentage,
        'highest_earning_country': highest_earning_country,
        'highest_earning_country_percentage':
        highest_earning_country_percentage,
        'top_IN_occupation': top_IN_occupation
    }
