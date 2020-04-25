import React from 'react'
import { graphql } from 'gatsby'

import Layout from '../components/layout'
import Content from '../components/Content'
import Wrapper from '../components/Wrapper'
import Hero from '../components/Hero'
import SEO from '../components/SEO'
import Bio from '../components/Bio';
import ArticleFooter from '../components/ArticleFooter';

export default props => {
  const { page, location } = props.data;
  const { html, frontmatter, excerpt } = page;
  const { title, slug, cover, date, tags } = frontmatter;

  return (
    <Layout location={location}>
      <SEO
        title={title}
        description={excerpt}
        path={slug}
        cover={cover && cover.publicURL}
      />

      <Hero
        heroImg={cover && cover.publicURL}
        title={title}
      />

      <Wrapper>
        <article>
          <Content md content={html} date={date} tags={tags} />
        </article>
        <ArticleFooter>
          <Bio />
        </ArticleFooter>
      </Wrapper>
    </Layout>
  )
}

export const pageQuery = graphql`
  query($slug: String!) {
    page: markdownRemark(frontmatter: { slug: { eq: $slug } }) {
      html
      excerpt
      frontmatter {
        title
        date(formatString: "MMMM DD, YYYY")
        slug
        cover {
          publicURL
        }
        tags
      }
    }
  }
`;
