using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using WebApplication_pizza.Models;

namespace WebApplication_pizza.Controllers
{
    public class sanphamsController : ApiController
    {
        private qlpizzaEntities14 db = new qlpizzaEntities14();

        // GET: api/sanphams
        public IQueryable<sanpham> Getsanphams()
        {
            return db.sanphams;
        }

        // GET: api/sanphams/5
        [ResponseType(typeof(sanpham))]
        public IHttpActionResult Getsanpham(string id)
        {
            sanpham sanpham = db.sanphams.Find(id);
            if (sanpham == null)
            {
                return NotFound();
            }

            return Ok(sanpham);
        }

        // PUT: api/sanphams/5
        [ResponseType(typeof(void))]
        public IHttpActionResult Putsanpham(string id, sanpham sanpham)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != sanpham.masp)
            {
                return BadRequest();
            }

            db.Entry(sanpham).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!sanphamExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/sanphams
        [ResponseType(typeof(sanpham))]
        public IHttpActionResult Postsanpham(sanpham sanpham)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.sanphams.Add(sanpham);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (sanphamExists(sanpham.masp))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = sanpham.masp }, sanpham);
        }

        // DELETE: api/sanphams/5
        [ResponseType(typeof(sanpham))]
        public IHttpActionResult Deletesanpham(string id)
        {
            sanpham sanpham = db.sanphams.Find(id);
            if (sanpham == null)
            {
                return NotFound();
            }

            db.sanphams.Remove(sanpham);
            db.SaveChanges();

            return Ok(sanpham);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool sanphamExists(string id)
        {
            return db.sanphams.Count(e => e.masp == id) > 0;
        }
    }
}