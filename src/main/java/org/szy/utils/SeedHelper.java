package org.szy.utils;

public class SeedHelper {
    public static final String[] categories = {
            "Food & Drink",
            "Health",
            "Music",
            "Auto, Boat & Air",
            "Charity & Causes",
            "Community",
            "Family & Education",
            "Fashion",
            "Film & Media",
            "Hobbies",
            "Home & Lifestyle",
            "Performing & Visual Arts",
            "Government",
            "Spirituality",
            "School Activities",
            "Science & Tech",
            "Holidays",
            "Sports & Fitness",
            "Travel & Outdoor",
            "Other"
    };
    public static final String Food = "Gourmet Cooking, Organic Foods, Vegan Diets, Healthy Snacks, Coffee Culture, Wine Tasting, Craft Beers, Gluten-Free Baking, Nutritional Supplements, Food Allergies";

    public static final String Health = "Mental Health Awareness, Yoga and Meditation, Physical Therapy, Alternative Medicine, Health Apps, First Aid Training, Health Insurance, Nutritional Counseling, Fitness Nutrition, Preventive Medicine";

    public static final String Music = "Music Production, Live Performances, Songwriting, Music History, Instruments, World Music, Music Festivals, DJing, Music Therapy, Music Education";

    public static final String Auto = "Car Maintenance, Boat Repair, Electric Vehicles, Car Customization, Classic Car Restoration, Auto Racing, Motorcycles, Aviation Enthusiast, Vehicle Safety, Marine Technology";

    public static final String Charity = "Charity Events, Fundraising Campaigns, Non-Profit Organizations, Volunteer Opportunities, Community Service, Disaster Relief, Social Justice, Environmental Conservation, Philanthropy, Human Rights";

    public static final String Community = "Local Festivals, Neighborhood Watch, Community Service, Community Gardens, Community Policing, Town Hall Meetings, Community Outreach, Neighborhood Associations, Community Events, Civic Engagement";

    public static final String Family = "Parenting Tips, Early Childhood Education, School Curriculum, Higher Education, Academic Research, Student Council, Parent-Teacher Associations, Educational Toys, Tutoring Services, School Safety";

    public static final String Fashion = "Fashion Trends, Sustainable Fashion, Personal Styling, Fashion Shows, Runway Models, Textile Design, Fashion Photography, Vintage Clothing, Street Style, Fashion Illustration";

    public static final String Film  = "Film Production, Movie Reviews, Music Videos, Podcasting, Television Production, Journalism, Media Literacy, Documentary Filmmaking, Animation, Digital Media";

    public static final String Hobbies = "Art and Crafts, Collecting, Gardening, DIY Projects, Board Games, Amateur Astronomy, Photography, Hiking, Cooking, Reading Clubs";

    public static final String Home = "Lifestyle: Home Decor, Interior Design, Sustainable Living, Eco-Friendly Products, Home Organization, Home Automation, Gardening, Home Maintenance, Smart Home Technology, Home Security";

    public static final String Performing = "Art Exhibitions, Painting Techniques, Sculpture Creation, Photography, Dance Performances, Theatre Productions, Film Acting, Stage Design, Visual Arts Education, Performance Art";

    public static final String Government = "Government Policies, Public Services, Elections, Legislative Changes, Public Administration, Civic Education, Government Transparency, National Security, Political Advocacy, International Relations";

    public static final String Spirituality = "Meditation Practices, Mindfulness, Religious Studies, Spiritual Retreats, Yoga Philosophy, Faith Communities, Prayer Groups, Spiritual Guidance, Sacred Texts, Rituals and Traditions";

    public static final String School = "School Clubs, Student Council, Academic Competitions, School Sports, School Plays, Field Trips, Debate Teams, School Newspaper, Student Volunteering, School Fundraisers";

    public static final String Science = "Science Experiments, Tech Innovations, Coding Workshops, Robotics, Artificial Intelligence, Machine Learning, Data Science, Space Exploration, Environmental Science, Biotechnology";

    public static final String Holidays = "Holiday Celebrations, Seasonal Events, Gift Ideas, Holiday Travel, Festive Recipes, Holiday Traditions, Holiday Decorations, Holiday Music, Holiday Movies, Holiday Parties";

    public static final String Sports = "Sports Training, Fitness Challenges, Sports Equipment, Team Sports, Individual Sports, Outdoor Adventures, Sports Nutrition, Sports Psychology, Physical Fitness, Sports Coaching";

    public static final String Travel = "Travel Destinations, Cultural Experiences, Travel Tips, Camping, Hiking, Wildlife Conservation, Ecotourism, Adventure Travel, Travel Photography, Travel Planning, Scenic Drives";

    public static final String Other = " Unique Interests, Niche Hobbies, Unconventional Careers, Alternative Lifestyles, Rare Collections, Emerging Trends, DIY Crafts, Personal Development, Creative Writing, Urban Exploration";

    public static final String[] FoodArr = Food.split(", ");
    public static final String[] HealthArr = Health.split(", ");
    public static final String[] MusicArr = Music.split(", ");
    public static final String[] AutoArr = Auto.split(", ");
    public static final String[] CharityArr = Charity.split(", ");
    public static final String[] CommunityArr = Community.split(", ");
    public static final String[] FamilyArr = Family.split(", ");
    public static final String[] FashionArr = Fashion.split(", ");
    public static final String[] FilmArr = Film.split(", ");
    public static final String[] HobbiesArr = Hobbies.split(", ");
    public static final String[] HomeArr = Home.split(", ");
    public static final String[] PerformingArr = Performing.split(", ");
    public static final String[] GovernmentArr = Government.split(", ");
    public static final String[] SpiritualityArr = Spirituality.split(", ");
    public static final String[] SchoolArr = School.split(", ");
    public static final String[] ScienceArr = Science.split(", ");
    public static final String[] HolidaysArr = Holidays.split(", ");
    public static final String[] SportsArr = Sports.split(", ");
    public static final String[] TravelArr = Travel.split(", ");
    public static final String[] OtherArr = Other.split(", ");
    public static final String[][] allEvents = {
            FoodArr, HealthArr, MusicArr, AutoArr, CharityArr,
            CommunityArr, FamilyArr, FashionArr, FilmArr, HobbiesArr,
            HomeArr, PerformingArr, GovernmentArr, SpiritualityArr,
            SchoolArr, ScienceArr, HolidaysArr, SportsArr, TravelArr, OtherArr
    };
    public static final String[] FoodFormat = {"Class", "Seminar", "Party"};
    public static final String[] HealthFormat = {"Class", "Seminar"};
    public static final String[] MusicFormat = {"Class", "Performance", "Party"};
    public static final String[] AutoFormat = {"Class"};
    public static final String[] CharityFormat = {"Seminar"};
    public static final String[] CommunityFormat = {"Networking"};
    public static final String[] FamilyFormat = {"Class", "Seminar"};
    public static final String[] FashionFormat = {"Festival", "Party", "Performance"};
    public static final String[] FilmFormat  = {"Class", "Seminar", "Networking"};
    public static final String[] HobbiesFormat = {"Game", "Networking", "Class"};
    public static final String[] HomeFormat = {"Class", "Seminar"};
    public static final String[] PerformingFormat = {"Festival", "Party", "Performance"};
    public static final String[] GovernmentFormat = {"Class", "Seminar", "Networking"};
    public static final String[] SpiritualityFormat = {"Class"};
    public static final String[] SchoolFormat = {"Class", "Seminar", "Networking"};
    public static final String[] ScienceFormat = {"Class", "Conference", "Seminar"};
    public static final String[] HolidaysFormat = {"Game", "Party"};
    public static final String[] SportsFormat = {"Game", "Class", "Party"};
    public static final String[] TravelFormat = {"Networking", "Party"};
    public static final String[] OtherFormat = {"Networking", "Party"};
    public static final String[][] allFormat = {
            FoodFormat, HealthFormat, MusicFormat, AutoFormat, CharityFormat,
            CommunityFormat, FamilyFormat, FashionFormat, FilmFormat, HobbiesFormat,
            HomeFormat, PerformingFormat, GovernmentFormat, SpiritualityFormat,
            SchoolFormat, ScienceFormat, HolidaysFormat, SportsFormat, TravelFormat, OtherFormat
    };

    public static final String[] city = {
            "New York",
            "Los Angeles",
            "Chicago",
            "Houston",
            "Phoenix",
            "Philadelphia",
            "San Antonio",
            "San Diego",
            "Dallas",
            "San Jose",
            "Austin",
            "Jacksonville",
            "Fort Worth",
            "Columbus",
            "San Francisco",
            "Charlotte",
            "Indianapolis",
            "Seattle",
            "Denver",
            "Washington",
            "Boston",
            "Nashville",
            "El Paso",
            "Detroit",
            "Memphis",
            "Portland",
            "Oklahoma City",
            "Las Vegas",
            "Louisville",
            "Baltimore",
            "Milwaukee",
            "Albuquerque",
            "Tucson",
            "Fresno",
            "Sacramento",
            "Mesa",
            "Kansas City",
            "Atlanta",
            "Long Beach",
            "Raleigh",
            "Omaha",
            "Miami",
            "Oakland",
            "Tulsa",
            "Cleveland",
            "Wichita",
            "Arlington",
            "New Orleans",
            "Tampa",
            "Bakersfield",
            "Aurora",
            "Anaheim",
            "Honolulu",
            "Minneapolis",
            "Santa Ana",
            "Riverside",
            "Corpus Christi",
            "Lexington",
            "St. Louis",
            "Pittsburgh",
            "Anchorage",
            "Stockton",
            "Cincinnati",
            "St. Paul",
            "Toledo",
            "Greensboro",
            "Newark",
            "Plano",
            "Henderson",
            "Lincoln",
            "Buffalo",
            "Jersey City",
            "Chula Vista",
            "Fort Wayne",
            "Orlando",
            "Norfolk",
            "Durham",
            "Chesapeake",
            "Irvine",
            "Madison",
            "Lubbock",
            "Winston-Salem",
            "Garland",
            "Glendale",
            "Hialeah",
            "Reno",
            "Chandigarh",
            "Laredo",
            "Scottsdale",
            "Montgomery",
            "Rochester",
            "Inglewood",
            "Spokane",
            "Tacoma",
            "Birmingham",
            "Des Moines",
            "Modesto",
            "Fayetteville",
            "Oxnard",
            "Fontana",
            "Columbus",
            "Akron",
            "Yonkers",
            "Frisco",
            "Shreveport",
            "Sioux Falls",
            "Aurora",
            "Worcester",
            "Little Rock",
            "Salt Lake City",
            "Huntington Beach",
            "Port St. Lucie",
            "Tallahassee",
            "Bellingham",
            "Hayward",
            "Unincorporated",
            "Rockford",
            "Knoxville",
            "Alexandria",
            "Eugene",
            "Cary",
            "Santa Clarita",
            "Olathe",
            "Concord",
            "Springfield",
            "Simi Valley",
            "Lancaster",
            "Escondido",
            "Fort Collins",
            "Pembroke Pines",
            "Coral Springs",
            "Sterling Heights",
            "Fullerton",
            "Columbia",
            "New Haven",
            "Elk Grove",
            "Bellevue",
            "Independence",
            "Miramar",
            "Berkeley",
            "Chattanooga",
            "Gilbert",
            "Carrollton",
            "Thousand Oaks",
            "Cambridge",
            "West Valley City",
            "Sandy Springs",
            "San Mateo",
            "Lafayette",
            "Surprise",
            "Denton",
            "Midland",
            "McKinney",
            "Billings",
            "Hillsboro",
            "Orange",
            "Wichita Falls",
            "Clarksville",
            "Topeka",
            "Fargo",
            "Norman",
            "Abilene",
            "Beaumont",
            "Provo",
            "Santa Rosa",
            "Peoria",
            "El Monte",
            "Gresham",
            "Green Bay",
            "Cicero",
            "Temecula",
            "Norwalk",
            "Richmond",
            "Murrieta",
            "Costa Mesa",
            "Pompano Beach"
    };

    public static final String[] addresses = {
            "1010 First Avenue, Apt 101",
            "2020 Second Street, Suite 202",
            "3030 Third Court, Apt 303",
            "4040 Fourth Lane, Room 404",
            "5050 Fifth Drive, Office 505",
            "6060 Sixth Way, Building 6",
            "7070 Seventh Place, Unit 707",
            "8080 Eighth Street, Apt 808",
            "9090 Ninth Avenue, Suite 909",
            "10010 Tenth Road, Building 10",
            "11112 Eleventh Path, Office 111",
            "12121 Twelfth Boulevard, Unit 121",
            "13131 Thirteenth Street, Room 131",
            "14141 Fourthteenth Court, Apt 141",
            "15151 Fifteenth Drive, Suite 151",
            "16161 Sixteenth Place, Building 16",
            "17171 Seventeenth Way, Unit 171",
            "18181 Eighteenth Street, Office 181",
            "19219 Nineteenth Avenue, Room 192",
            "20221 Twentieth Road, Suite 202",
            "21230 Twenty-first Path, Apt 213",
            "22222 Twenty-second Boulevard"
    };

    public static final String[] images = {
            "1.jpeg",
            "2.jpg",
            "3.jpg",
            "4.jpeg",
            "5.jpeg",
            "6.jpeg"
    };

    public static final String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus eu sem tempor, lobortis vel odio nec, vehicula augue. Cras ultricies ligula sed magna dictum porta. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Quisque porttitor, elit nec nonummy ultrices, purus lobortis nisl nisl lidaelit. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor molestie lacinia sem. Ut vel enim varius nisl aenean ipsum ligula, sed eleifend laoreet eleifend pede";
}
