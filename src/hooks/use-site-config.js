import { useStaticQuery, graphql } from 'gatsby'

const useSiteMetadata = () => {
  const result = useStaticQuery(graphql`
    {
      site {
        siteMetadata {
          siteTitle
          siteCover
          authorName
          authorAvatar
          authorDescription
          siteDescription
          defaultLang
          headerTitle
          headerLinksIcon
          headerLinks {
            label
            url
          }
          websiteHost {
            name
            url
          }
          footerLinks {
            sectionName
            links {
              label
              url
            }
          }
        }
      }
    }
  `);
  return result.site.siteMetadata
};

export default useSiteMetadata
